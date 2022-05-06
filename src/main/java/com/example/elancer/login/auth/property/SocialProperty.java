package com.example.elancer.login.auth.property;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "spring.social.google")
@Getter
@RequiredArgsConstructor
public class SocialProperty {

    private final String clientId;
    private final String clientSecret;
    private final String redirect;
    private final String urlLogin;
    private final String urlToken;
    private final String urlProfile;


}
