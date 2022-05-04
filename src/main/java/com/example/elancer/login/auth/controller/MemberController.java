package com.example.elancer.login.auth.controller;

import com.example.elancer.jwt.JwtTokenProvider;
import com.example.elancer.login.auth.dto.MemberLoginRequest;
import com.example.elancer.login.auth.exception.UserIdNotFoundException;
import com.example.elancer.login.auth.exception.UserPasswordException;
import com.example.elancer.member.domain.Member;
import com.example.elancer.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public String login(@RequestBody MemberLoginRequest loginRequest) {
        log.info("userId = {}", loginRequest.getUserId());
        Member findMember = memberRepository.findByUserId(loginRequest.getUserId()).orElseThrow(UserIdNotFoundException::new);
        if (!passwordEncoder.matches(loginRequest.getPassword(), findMember.getPassword()))
            throw new UserPasswordException();

        return jwtTokenProvider.createToken(findMember.getUserId(), findMember.getRole());
    }


    @PostMapping("/test")
    public String test(){

        return "<h1>test 통과</h1>";
    }
}
