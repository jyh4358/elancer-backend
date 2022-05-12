package com.example.elancer.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberOAuthLoginResponse {
    private String username;
    private String email;

    private String accessToken;
    private String refreshToken;
}
