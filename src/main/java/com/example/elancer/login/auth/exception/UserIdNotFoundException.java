package com.example.elancer.login.auth.exception;

import org.springframework.security.core.AuthenticationException;

public class UserIdNotFoundException extends AuthenticationException {

    private static final String MESSAGE = "아이디를 찾지 못했습니다. 다시 확인해 주세요.";

    public UserIdNotFoundException() {
        super(MESSAGE);
    }
}
