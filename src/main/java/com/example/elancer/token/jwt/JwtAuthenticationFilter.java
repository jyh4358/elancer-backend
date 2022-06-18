package com.example.elancer.token.jwt;

import com.example.elancer.token.exception.CustomJwtExpiredException;
import com.example.elancer.token.exception.CustomJwtMalformedException;
import com.example.elancer.token.exception.CustomJwtSignatureException;
import com.example.elancer.token.exception.CustomRefreshJwtExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        String refreshToken = jwtTokenProvider.resolveRefreshToken((HttpServletRequest) request);

        log.info("JwtAuthenticationFilter 실행");

        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");

        try {
            if (refreshToken == null) {
                if (token != null && jwtTokenProvider.validateToken(token)) {
                    Authentication authentication = jwtTokenProvider.getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            chain.doFilter(request, response);

        } catch (CustomJwtExpiredException e) {
            Map<String, String> map = new HashMap<>();

            map.put("errortype", "Forbidden");
            map.put("code", "401");
            map.put("message", "access 토큰이 만료되었습니다. Refresh 토큰이 필요합니다.");

            log.error("만료된 토큰");
            response.getWriter().write(objectMapper.writeValueAsString(map));
            log.info("생성된 response = {}", response);
        } catch (CustomRefreshJwtExpiredException e) {
            Map<String, String> map = new HashMap<>();

            map.put("errortype", "Forbidden");
            map.put("code", "403");
            map.put("message", "refresh 토큰이 만료되었습니다. 다시 로그인을 해주세요.");

        } catch (CustomJwtMalformedException | CustomJwtSignatureException e) {
            Map<String, String> map = new HashMap<>();

            map.put("errortype", "Forbidden");
            map.put("code", "402");
            map.put("message", "변조된 토큰입니다. 다시 로그인을 해주세요.");


            log.error("변조된 토큰");
            response.getWriter().write(objectMapper.writeValueAsString(map));
        }



    }
}
