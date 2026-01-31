package com.authentication.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.security.jwt")
@Data
public class JwtProperties {
    private String signingKey;
    private Long expirationMs;
}
