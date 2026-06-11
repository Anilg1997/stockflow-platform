package com.stockflow.gateway.config;

import com.stockflow.common.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        return new JwtTokenProvider(secret, expiration * 1000, refreshExpiration * 1000);
    }
}
