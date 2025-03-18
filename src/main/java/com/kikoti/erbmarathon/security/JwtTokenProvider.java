package com.kikoti.erbmarathon.security;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kikoti.erbmarathon.entity.JwtToken;
import com.kikoti.erbmarathon.entity.UserPrincipal;
import com.kikoti.erbmarathon.entity.user.User;
import com.kikoti.erbmarathon.exception.ApiRequestException;
import com.kikoti.erbmarathon.repository.JwtTokenRepository;
import com.kikoti.erbmarathon.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@AllArgsConstructor
@Component
public class JwtTokenProvider {

    @Getter
    @Setter
    private static String staticSecret;
    private final UserRepository userRepository;
    private final JwtTokenRepository jwtTokenRepository;

    @Autowired
    JwtConfig jwtConfig;

    private static Key getSignKey() {
        if (staticSecret == null) {
            throw new IllegalStateException("Static secret is not initialized");
        }
        byte[] keyBytes = Decoders.BASE64.decode(staticSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static Map<String, Object> decodeToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(getSignKey())
                    .build().parseClaimsJws(token).getBody();

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.convertValue(claims, new TypeReference<>() {});
        } catch (ExpiredJwtException ex) {
            throw new ApiRequestException("Token Expired, Login", HttpStatus.UNAUTHORIZED);
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException ex) {
            throw new ApiRequestException("Invalid Token, Login", HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            throw new ApiRequestException(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    public static Object getJWTBody(String key, String jwt) throws ExpiredJwtException, SignatureException {
        Assert.hasText(key, "Encryption key should not be null or empty");
        byte[] encryptionKeyBytes = Base64.getDecoder().decode(key);
        Key signingKey = new SecretKeySpec(encryptionKeyBytes, "SH512");
        return Jwts.parserBuilder().setSigningKey(getSignKey())
                .build().parseClaimsJws(jwt) .getBody();
    }

    public void initialize() {
        String jwtSecret = jwtConfig.getSecret();
        setStaticSecret(jwtSecret);
    }

    @PostConstruct
    public void init() {
        initialize();
    }

    public String generateToken(UserPrincipal userPrincipal) {

        String jwtSecret = jwtConfig.getSecret();
        String jwt_expiration_time = jwtConfig.getJwt_expiration_time();
        long jwtExpirationMs = Long.parseLong(jwt_expiration_time);
        User user = userRepository.findByEmail(userPrincipal.getUsername())
                .orElseThrow(() -> new ApiRequestException("User not found", HttpStatus.NOT_FOUND));
        Claims claims = Jwts.claims();
        populateClaims(claims, user);
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder().setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate)
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private void populateClaims(Claims claims, User user) {
        claims.put("sub", user.getEmail());
        claims.put("email", user.getEmail());
    }

    public String extractTokenFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // Extract token
        }
        return null; // No token found
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token) {
        try {
            // Create a JwtParser instance using the secret key
            return Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException ex) {
            // Token expired
            throw new ApiRequestException("Token Expired, Login", HttpStatus.UNAUTHORIZED);
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException ex) {
            // Invalid token
            throw new ApiRequestException("Invalid Token, Login", HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            // Other exceptions
            throw new ApiRequestException(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    private void validateTokenInRepository(String token) {
        JwtToken newToken = jwtTokenRepository.findTokenByData(token);
        if (newToken != null) {
            if (newToken.isRevoked()) {
                throw new ApiRequestException("Token is revoked", HttpStatus.UNAUTHORIZED);
            }
        } else {
            throw new ApiRequestException("Token not found", HttpStatus.UNAUTHORIZED);
        }
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {

        try {
            // Create a JwtParser instance using the secret key
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claimsResolver.apply(claims);
        } catch (ExpiredJwtException ex) {
            // Token expired
            throw new ApiRequestException("Token Expired, Login", HttpStatus.UNAUTHORIZED);
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException ex) {
            // Invalid token
            throw new ApiRequestException("Invalid Token, Login", HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            // Other exceptions
            throw new ApiRequestException(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}