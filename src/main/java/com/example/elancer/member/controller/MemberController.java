package com.example.elancer.member.controller;

import com.example.elancer.token.dto.*;
import com.example.elancer.token.service.JwtTokenService;
import com.example.elancer.login.auth.dto.AuthCode;
import com.example.elancer.member.dto.MemberLoginRequest;
import com.example.elancer.member.dto.MemberLoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final JwtTokenService jwtTokenService;


    @PostMapping("/login")
    public ResponseEntity<MemberLoginResponse> login(@RequestBody MemberLoginRequest loginRequest, HttpServletResponse response) {
        MemberLoginResponse loginResponse = jwtTokenService.loginMember(loginRequest);
        response.addCookie(getCookie(loginResponse.getRefreshToken()));
        return new ResponseEntity(loginResponse, HttpStatus.OK);
    }


    @PostMapping("/test")
    public String test(){

        return "<h1>test 통과</h1>";
    }

    @PostMapping("/login/google")
    public MemberLoginResponse loginByKakao(@RequestBody AuthCode authCode, HttpServletResponse response) {
        System.out.println("login/google 호출 ===========");
        System.out.println("code = " + authCode.getCode());
        MemberLoginResponse responseDto = jwtTokenService.loginMemberByProvider(authCode.getCode());
        System.out.println("login/google 로직 이후 ===========");
        response.addCookie(getCookie(responseDto.getRefreshToken()));

        return responseDto;
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenResponse> reIssue(@RequestBody TokenRequest tokenRequestDto, HttpServletResponse response) {
        TokenResponse responseDto = jwtTokenService.reIssue(tokenRequestDto);
        response.addCookie(getCookie(responseDto.getRefreshToken()));
        return new ResponseEntity(responseDto, HttpStatus.OK);
    }

    private Cookie getCookie(String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setMaxAge(30 * 24 * 60 * 60);
        cookie.setSecure(true);   // https 통신이 아닌경우에는 쿠키를 전송하지 않음
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        return cookie;
    }
}
