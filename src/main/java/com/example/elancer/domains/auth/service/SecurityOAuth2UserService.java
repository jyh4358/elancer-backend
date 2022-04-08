package com.example.elancer.domains.auth.service;

import com.example.elancer.domains.auth.dto.MemberDetails;
import com.example.elancer.domains.auth.dto.OAuthAttributes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityOAuth2UserService extends DefaultOAuth2UserService {


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String id = createId(userRequest, oAuth2User);
        String name = oAuth2User.getAttribute("name");
        String email = oAuth2User.getAttribute("email");
        System.out.println("oAuth2User.getAttributes() = " + oAuth2User.getAttributes());

//        MemberDetails memberDetails = MemberDetails.oauth2UserOf(id, name, email, oAuth2User.getAttributes());
        OAuthAttributes oauthAttributes = OAuthAttributes.of(id, oAuth2User.getAttributes());
//        return memberDetails;
        return oauthAttributes;
    }


    private String createId(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        String provider = userRequest.getClientRegistration().getRegistrationId();
        String providerId = oAuth2User.getAttribute("sub");
        String id = provider + "_" + providerId;
        return id;
    }
}
