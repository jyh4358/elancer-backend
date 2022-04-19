package com.example.elancer.login.auth.service;

import com.example.elancer.login.auth.dto.OAuthAttributes;
import com.example.elancer.login.auth.exception.MemberDuplicateException;
import com.example.elancer.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {


        OAuth2User oAuth2User = super.loadUser(userRequest);
        String createUserId = createId(userRequest, oAuth2User);
//        memberRepository.findByUserId(createUserId).orElseThrow(() -> new InternalAuthenticationServiceException("소셜 회원 가입이 안되어있습니다."));
        OAuthAttributes oauthAttributes = OAuthAttributes.of(createUserId, oAuth2User.getAttributes());

        return oauthAttributes;
    }


    private String createId(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        String provider = userRequest.getClientRegistration().getRegistrationId();
        String providerId = oAuth2User.getAttribute("sub");
        String id = provider + "_" + providerId;
        return id;
    }
}
