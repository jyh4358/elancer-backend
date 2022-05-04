package com.example.elancer.common.factory;

import com.example.elancer.common.annotation.WithMockCustomUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.lang.annotation.Annotation;
import java.util.Arrays;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser withMockCustomUser) {
        final SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

        final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                withMockCustomUser.userId(),
                withMockCustomUser.password(),
                Arrays.asList(new SimpleGrantedAuthority(withMockCustomUser.role()))
        );

        securityContext.setAuthentication(usernamePasswordAuthenticationToken);
        return securityContext;
    }
}
