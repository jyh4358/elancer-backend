package com.example.elancer.integrate.freelancer;

import com.example.elancer.member.dto.MemberLoginRequest;
import com.example.elancer.member.dto.MemberLoginResponse;
import com.example.elancer.token.service.JwtTokenService;

public class LoginHelper {
    public static MemberLoginResponse 로그인(String userId, JwtTokenService jwtTokenService) {
        MemberLoginRequest memberLoginRequest = new MemberLoginRequest(userId, "pwd");
        return jwtTokenService.loginMember(memberLoginRequest);
    }
}
