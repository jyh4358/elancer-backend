package com.example.elancer.common.annotation;

import com.example.elancer.common.factory.WithMockCustomUserSecurityContextFactory;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {

    String userId();
    String password();
    String role() default "ADMIN";
}
