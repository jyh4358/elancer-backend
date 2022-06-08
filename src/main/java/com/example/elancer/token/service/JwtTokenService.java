package com.example.elancer.token.service;

import com.example.elancer.login.auth.dto.GoogleProfile;
import com.example.elancer.login.auth.exception.UserIdNotFoundException;
import com.example.elancer.login.auth.exception.UserPasswordException;
import com.example.elancer.login.auth.service.ProviderService;
import com.example.elancer.member.domain.Member;
import com.example.elancer.member.dto.MemberLoginRequest;
import com.example.elancer.member.dto.MemberLoginResponse;
import com.example.elancer.member.dto.MemberOAuthLoginResponse;
import com.example.elancer.member.exception.NotExistMemberException;
import com.example.elancer.member.repository.MemberRepository;
import com.example.elancer.token.dto.TokenResponse;
import com.example.elancer.token.exception.InvalidRefreshTokenException;
import com.example.elancer.token.jwt.AccessToken;
import com.example.elancer.token.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JwtTokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProviderService providerService;


    /**
     * 회원 로그인
     * @param requestDto
     * @return
     */
    @Transactional
    public MemberLoginResponse loginMember(MemberLoginRequest requestDto) {
        Member member = memberRepository.findByUserId(requestDto.getUserId()).orElseThrow(UserIdNotFoundException::new);
        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword()))
            throw new UserPasswordException();
        member.updateRefreshToken(jwtTokenProvider.createRefreshToken());
        return new MemberLoginResponse(member.getRole(), jwtTokenProvider.createToken(member.getUserId()), member.getRefreshToken());
    }

    /**
     * 소셜 로그인
     * @param code
     * @return
     */
    @Transactional
    public MemberOAuthLoginResponse loginMemberByProvider(String code) {
        AccessToken accessToken = providerService.getAccessToken(code);
        GoogleProfile googleProfile = providerService.getProfile(accessToken.getAccess_token());

        // userId는 구글에서 가져온 email key(sub)을 이용하여 만듬. ex) google_12321321321
        String userId = createUserId(googleProfile.getSub());

        Optional<Member> findMember = memberRepository.findByUserId(userId);
        if (findMember.isPresent()) {
            Member member = findMember.get();
            member.updateRefreshToken(jwtTokenProvider.createRefreshToken());
            return new MemberOAuthLoginResponse(
                    member.getName(),
                    member.getEmail(),
                    jwtTokenProvider.createToken(member.getUserId()),
                    member.getRefreshToken());
        } else {
            return new MemberOAuthLoginResponse(
                    userId,
                    googleProfile.getEmail(),
                    "",
                    "");
        }
    }

    @Transactional
    public TokenResponse reIssue(HttpServletRequest request) {
        if (!jwtTokenProvider.validateToken(jwtTokenProvider.resolveRefreshToken(request)))
            throw new InvalidRefreshTokenException();

        Member member = memberRepository.findByRefreshToken(
                jwtTokenProvider.resolveRefreshToken(request)).orElseThrow(NotExistMemberException::new);

        if (!member.getRefreshToken().equals(jwtTokenProvider.resolveRefreshToken(request)))
            throw new InvalidRefreshTokenException();

        String accessToken = jwtTokenProvider.createToken(member.getUserId());
        String refreshToken = jwtTokenProvider.createRefreshToken();
        member.updateRefreshToken(refreshToken);
        return new TokenResponse(accessToken, refreshToken);
    }

    private String createUserId(String sub) {
        String userId = "google" + "_" + sub;
        return userId;
    }
}
