package com.example.elancer.login.auth.service;

import com.example.elancer.token.jwt.AccessToken;
import com.example.elancer.login.auth.dto.GoogleProfile;
import com.example.elancer.login.auth.dto.OAuthRequest;
import com.example.elancer.login.auth.dto.OAuthRequestFactory;
import com.example.elancer.login.auth.property.SocialProperty;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProviderService {

    private final RestTemplate restTemplate;
    private final OAuthRequestFactory oAuthRequestFactory;
    private final Gson gson;

    public GoogleProfile getProfile(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);

        String profileUrl = oAuthRequestFactory.getProfileUrl();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> response = restTemplate.postForEntity(profileUrl, request, String.class);

        try {
            if (response.getStatusCode() == HttpStatus.OK) {
                return extractProfile(response);
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        throw new RuntimeException();
    }

    private GoogleProfile extractProfile(ResponseEntity<String> response) {
        return gson.fromJson(response.getBody(), GoogleProfile.class);
    }


    public AccessToken getAccessToken(String code) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);


        OAuthRequest oAuthRequest = oAuthRequestFactory.getRequest(code);
        HttpEntity<OAuthRequest> request = new HttpEntity<>(oAuthRequest, httpHeaders);

        System.out.println(" ===========================");
        System.out.println("oAuthRequest = " + oAuthRequest);
        ResponseEntity<String> response = restTemplate.postForEntity(oAuthRequestFactory.getTokenUrl(), request, String.class);
        System.out.println(" ===========================");



        try {
            if (response.getStatusCode() == HttpStatus.OK) {
                return gson.fromJson(response.getBody(), AccessToken.class);
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        throw new RuntimeException();
    }
}
