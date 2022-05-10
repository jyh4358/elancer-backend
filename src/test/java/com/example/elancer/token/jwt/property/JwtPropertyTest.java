package com.example.elancer.token.jwt.property;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("h2")
@SpringBootTest
class JwtPropertyTest {

    @Autowired
    private JwtProperty jwtProperty;

    @Test
    @DisplayName("Jwt 관련 정보를 읽어 온다.")
    void jwtPropertyTest() {
        String secretKey = jwtProperty.getSecretKey();
        Long accessExpired = jwtProperty.getAccessExpired();
        Long refreshExpired = jwtProperty.getRefreshExpired();
    }

}