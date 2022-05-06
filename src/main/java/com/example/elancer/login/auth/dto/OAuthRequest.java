package com.example.elancer.login.auth.dto;

import lombok.*;
import org.springframework.util.LinkedMultiValueMap;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class OAuthRequest {
//    private String url;
//    private LinkedMultiValueMap<String, String> map;

    private String clientId;
    private String redirectUri;
    private String clientSecret;
    private String code;
    private String grantType;
}
