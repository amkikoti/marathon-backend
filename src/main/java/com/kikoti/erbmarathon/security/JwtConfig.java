package com.kikoti.erbmarathon.security;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class JwtConfig {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.prefix}")
    private String prefix;

    @Value("${jwt.header}")
    private String header;

    @Value("${jwt.expiration.time}")
    private String jwt_expiration_time;

    @Value("${api.base.url}")
    private String baseUrl;
}
