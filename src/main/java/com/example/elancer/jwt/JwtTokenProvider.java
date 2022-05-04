package com.example.elancer.jwt;

import com.example.elancer.jwt.property.JwtProperty;
import com.example.elancer.login.auth.service.SecurityUserDetailsService;
import com.example.elancer.member.domain.MemberType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    public static final String AUTHORITIES_KEY = "Authorization";

    private final String secretKey;
    private final Long accessExpired;
    private final Long refreshExpired;

    private final SecurityUserDetailsService securityUserDetailsService;


    public JwtTokenProvider(JwtProperty property, SecurityUserDetailsService securityUserDetailsService) {
        this.secretKey = Base64.getEncoder().encodeToString(property.getSecretKey().getBytes());
        this.accessExpired = property.getAccessExpired();
        this.refreshExpired = property.getRefreshExpired();
        this.securityUserDetailsService = securityUserDetailsService;
    }

    public String createToken(String userId, MemberType role) {
        Claims claims = Jwts.claims().setSubject(userId); // JWT payload 에 저장되는 정보단위, 보통 여기서 user를 식별하는 값을 넣는다.
        claims.put("roles", role); // 정보는 key / value 쌍으로 저장된다.
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + accessExpired)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();
    }

    public String createRefreshToken() {
        Date now = new Date();

        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshExpired))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = securityUserDetailsService.loadUserByUsername(this.getUserId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserId(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();

    }

    // Request의 Header에서 token 값을 가져옵니다. "Authorization" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(AUTHORITIES_KEY);
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            // 만료된 토큰이면 ExpiredJwtException 예외를 던져준다.
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return true;
        } catch (ExpiredJwtException e) {
            return false;
        }
    }


}
