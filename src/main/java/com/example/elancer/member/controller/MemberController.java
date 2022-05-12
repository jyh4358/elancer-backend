package com.example.elancer.member.controller;

import com.example.elancer.member.dto.MemberOAuthLoginResponse;
import com.example.elancer.token.dto.*;
import com.example.elancer.token.jwt.JwtTokenProvider;
import com.example.elancer.token.service.JwtTokenService;
import com.example.elancer.login.auth.dto.AuthCode;
import com.example.elancer.member.dto.MemberLoginRequest;
import com.example.elancer.member.dto.MemberLoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final JwtTokenService jwtTokenService;

    /**
     * 회원 로그인
     * @param loginRequest
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<MemberLoginResponse> login(
            @RequestBody MemberLoginRequest loginRequest
    ) {
        MemberLoginResponse loginResponse = jwtTokenService.loginMember(loginRequest);
        return new ResponseEntity(loginResponse, HttpStatus.OK);
    }


    /**
     * 소셜 로그인
     * @param authCode
     * @return
     */
    @PostMapping("/login/google")
    public MemberOAuthLoginResponse loginBy(@RequestBody AuthCode authCode) {

        MemberOAuthLoginResponse responseDto = jwtTokenService.loginMemberByProvider(authCode.getCode());

        return responseDto;
    }


    /**
     * token 재발급
     * @param tokenRequestDto
     * @param response
     * @return
     */
    @PostMapping("/reissue")
    public ResponseEntity<TokenResponse> reIssue(@RequestBody TokenRequest tokenRequestDto, HttpServletResponse response) {
        TokenResponse responseDto = jwtTokenService.reIssue(tokenRequestDto);
        return new ResponseEntity(responseDto, HttpStatus.OK);
    }

    // local repository를 사용하는 방식으로, cookie 사용 x
//    private Cookie getCookie(String refreshToken) {
//        Cookie cookie = new Cookie("refreshToken", refreshToken);
//        cookie.setMaxAge(30 * 24 * 60 * 60);
//        cookie.setSecure(true);   // https 통신이 아닌경우에는 쿠키를 전송하지 않음
//        cookie.setHttpOnly(true);
//        cookie.setPath("/");
//        return cookie;
//    }
}
