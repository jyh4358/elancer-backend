package com.example.elancer.jwt.property;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "jwt")
@Getter
@RequiredArgsConstructor
public class JwtProperty {

    private final String header;
    private final String secretKey;
    private final Long accessExpired;
    private final Long refreshExpired;
}
