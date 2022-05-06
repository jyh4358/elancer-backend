package com.example.elancer.token.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
public class Jwt {
    private static final String ACCOUNT_NUM = "num";
    private static final String ROLES = "roles";

    private final String secretKey;
    private final int expirySeconds;
    private final Algorithm algorithm;
    private final JWTVerifier jwtVerifier;

    public Jwt(String secretKey, int expirySeconds, Algorithm algorithm, JWTVerifier jwtVerifier) {
        this.secretKey = secretKey;
        this.expirySeconds = expirySeconds;
        this.algorithm = Algorithm.HMAC512(secretKey);
        this.jwtVerifier = jwtVerifier;
    }

    public String sign(Claims claims) {
        Date now = new Date();
        JWTCreator.Builder builder = JWT.create();
        builder.withIssuedAt(now);
        if (expirySeconds > 0) {
            builder.withExpiresAt(new Date(now.getTime() + expirySeconds * 1000L));
        }
        builder.withClaim(ACCOUNT_NUM, claims.num);
        builder.withClaim(ROLES, claims.role);
        return builder.sign(algorithm);
    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Claims {

        private Long num;
        private String role;
        private Date issuedAt;
        private Date expiration;

        public Claims(DecodedJWT decodedJWT) {
            Claim num = decodedJWT.getClaim(ACCOUNT_NUM);
            if (!num.isNull()) {
                this.num = num.asLong();
            }
            Claim roles = decodedJWT.getClaim(ROLES);
            if (!roles.isNull()) {
                this.role = String.valueOf(role);
            }
            this.issuedAt = decodedJWT.getIssuedAt();
            this.expiration = decodedJWT.getExpiresAt();
        }

        public static Claims from(Long num, String role) {
            Claims claims = new Claims();
            claims.num = num;
            claims.role = role;
            return claims;
        }

        public Map<String, Object> asMap() {
            Map<String, Object> map = new HashMap<>();
            map.put("num", num);
            map.put("role", role);
            map.put("issuedAt", checkedIssuedAt());
            map.put("expiration", checkedExpiration());
            return map;
        }


        private long checkedIssuedAt() {
            return issuedAt != null ? issuedAt.getTime() : -1;
        }

        private long checkedExpiration() {
            return expiration != null ? expiration.getTime() : -1;
        }
    }
}

