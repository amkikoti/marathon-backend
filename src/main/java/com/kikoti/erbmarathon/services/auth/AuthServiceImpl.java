package com.kikoti.erbmarathon.services.auth;
import com.kikoti.erbmarathon.dtos.*;
import com.kikoti.erbmarathon.entity.JwtToken;
import com.kikoti.erbmarathon.entity.user.User;
import com.kikoti.erbmarathon.entity.UserPrincipal;
import com.kikoti.erbmarathon.enums.UserStatus;
import com.kikoti.erbmarathon.exception.ApiRequestException;
import com.kikoti.erbmarathon.repository.JwtTokenRepository;
import com.kikoti.erbmarathon.repository.UserRepository;
import com.kikoti.erbmarathon.security.JwtConfig;
import com.kikoti.erbmarathon.security.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenRepository jwtTokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;

    @Override
    public ResponseEntity<?> register(SignupDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ApiRequestException("Email has already been used", HttpStatus.UNAUTHORIZED);
        }

        try {
            User user = User.builder()
                    .firstName(StringUtils.capitalize(request.getFirstName().toLowerCase()))
                    .lastName(StringUtils.capitalize(request.getLastName().toLowerCase()))
                    .email(request.getEmail())
                    .archive(0)
                    .password(passwordEncoder.encode(request.getPassword()))
                    .status(UserStatus.NEEDPASSWORDCHANGE)
                    .build();

            user = userRepository.save(user);
            Map<String, Object> response = new HashMap<>();
            response.put("data", getUserData(user));
            response.put("message", "User created successfully");
            response.put("success", true);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> authenticate(SigninDto request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtTokenProvider.generateToken(userPrincipal);
            jwtTokenRepository.save(new JwtToken(token));

            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new ApiRequestException("User not found", HttpStatus.UNAUTHORIZED));

            Map<String, Object> response = new HashMap<>();
            response.put("AccessToken", token);
            response.put("data", getUserData(user));
            response.put("message", "User logged in successfully");
            response.put("success", true);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            throw new ApiRequestException("Bad credentials", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<?> resetPassword(@Valid ResetPasswordDto request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ApiRequestException("User not found", HttpStatus.BAD_REQUEST));

        String code = String.valueOf(new Random().nextInt(900000) + 100000);
        user.setRememberCode(code);
        userRepository.save(user);

        Map<String, Object> response = new HashMap<>();
        response.put("data", "Use this code: " + code + " to reset your account password.");
        response.put("success", true);
        response.put("message", "Successfully sent token");
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> confirmResetPassword(@Valid ConfirmResetPasswordDto request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new ApiRequestException("Passwords do not match", HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findUserByEmailAndRememberCode(request.getEmail(), String.valueOf(request.getConfirmationCode()))
                .orElseThrow(() -> new ApiRequestException("Wrong confirmation code", HttpStatus.BAD_REQUEST));

        user.setRememberCode("0");
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "Password changed successfully"));
    }

    @Override
    public ResponseEntity<?> resetUserPassword(@Valid ResetPasswordDto request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ApiRequestException("User not found", HttpStatus.BAD_REQUEST));

        String newPassword = "Afbo" + (new Random().nextInt(9000) + 1000);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return ResponseEntity.ok(Map.of(
                "message", "Password reset successfully",
                "newPassword", newPassword
        ));
    }

    @Override
    public ResponseEntity<?> changeUserStatus(@Valid ChangeStatusDto request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ApiRequestException("User not found", HttpStatus.BAD_REQUEST));

        user.setStatus(request.getStatus());
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "User status changed successfully"));
    }

    @Override
    public ResponseEntity<?> changePassword(@Valid ChangePasswordDto request, String auth) {
        if (Objects.equals(request.getOldPassword(), request.getNewPassword())) {
            throw new ApiRequestException("New password can't be the same as old password", HttpStatus.BAD_REQUEST);
        }

        String token = auth.replace("Bearer ", "");
        String email = (String) JwtTokenProvider.decodeToken(token).get("email");

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiRequestException("User not found", HttpStatus.BAD_REQUEST));

        if (!new BCryptPasswordEncoder().matches(request.getOldPassword(), user.getPassword())) {
            throw new ApiRequestException("Incorrect old password", HttpStatus.BAD_REQUEST);
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "Password changed successfully"));
    }

    private Map<String, Object> getUserData(User user) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("firstName", user.getFirstName());
        userData.put("lastName", user.getLastName());
        userData.put("email", user.getEmail());
        userData.put("status", user.getStatus());
        return userData;
    }
}