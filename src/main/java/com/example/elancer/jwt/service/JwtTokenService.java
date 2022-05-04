package com.example.elancer.jwt.service;

import com.example.elancer.jwt.JwtTokenProvider;
import com.example.elancer.jwt.dto.TokenRequest;
import com.example.elancer.jwt.dto.TokenResponse;
import com.example.elancer.member.domain.Member;
import com.example.elancer.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JwtTokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


//    @Transactional
//    public TokenResponse reIssue(TokenRequest requestDto) {
//        if (!jwtTokenProvider.validateToken(requestDto.getRefreshToken()))
//            throw new InvalidRefreshTokenException();
//
//        Member member = findMemberByToken(requestDto);
//
//        if (!member.getRefreshToken().equals(requestDto.getRefreshToken()))
//            throw new InvalidRefreshTokenException();
//
//        String accessToken = jwtTokenProvider.createToken(member.getUserId());
//        String refreshToken = jwtTokenProvider.createRefreshToken();
//        member.updateRefreshToken(refreshToken);
//        return new TokenResponseDto(accessToken, refreshToken);
//    }
//
//    public Member findMemberByToken(TokenRequest requestDto) {
//        Authentication auth = jwtTokenProvider.getAuthentication(requestDto.getAccessToken());
//        UserDetails userDetails = (UserDetails) auth.getPrincipal();
//        String username = userDetails.getUsername();
//        return memberRepository.findByEmail(username).orElseThrow(MemberNotFoundException::new);
//    }
}
