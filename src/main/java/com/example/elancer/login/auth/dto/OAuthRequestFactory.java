package com.example.elancer.login.auth.dto;

import com.example.elancer.login.auth.property.SocialProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuthRequestFactory {

    private final SocialProperty socialProperty;

    public OAuthRequest getRequest(String code) {

//        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//
//        map.add("grant_type", "authorization_code");
//        map.add("client_id", socialProperty.getClientId());
//        map.add("client_secret", socialProperty.getClientSecret());
//        map.add("redirect_uri", socialProperty.getRedirect());
//        map.add("code", code);
//
//        return new OAuthRequest(socialProperty.getUrlToken(), map);
        OAuthRequest oAuthRequest = OAuthRequest.builder()
                .clientId(socialProperty.getClientId())
                .clientSecret(socialProperty.getClientSecret())
                .code(code)
                .redirectUri(socialProperty.getRedirect())
                .grantType("authorization_code")
                .build();
        return oAuthRequest;
    }

    public String getProfileUrl() {
        return socialProperty.getUrlProfile();
    }
    public String getTokenUrl() {
        return socialProperty.getUrlToken();
    }
}
