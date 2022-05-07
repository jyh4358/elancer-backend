package com.example.elancer.login.auth.dto;

import lombok.*;
import org.springframework.util.LinkedMultiValueMap;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OAuthRequest {

    private String clientId;
    private String redirectUri;
    private String clientSecret;
    private String code;
    private String grantType;
}
