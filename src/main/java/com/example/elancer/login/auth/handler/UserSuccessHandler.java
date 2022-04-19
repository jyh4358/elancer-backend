package com.example.elancer.login.auth.handler;

import com.example.elancer.login.auth.dto.OAuthAttributes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class UserSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("==================================");
        log.info("onAuthenticationSuccess");
        log.info("authentication={}", authentication);

        OAuthAttributes principal = (OAuthAttributes) authentication.getPrincipal();

        // todo handler에서 repository에 접근하는건 괜찮나...?



        redirectStrategy.sendRedirect(request, response, "/");

    }
}
