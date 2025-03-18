package com.kikoti.erbmarathon.security;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class JwtConfig {

    @Value("${jwt_secret}")
    private String secret;

    @Value("${jwt_prefix}")
    private String prefix;

    @Value("${jwt_header}")
    private String header;

    @Value("${jwt_expiration_time}")
    private String jwt_expiration_time;

    @Value("${api_base_url}")
    private String baseUrl;
}
