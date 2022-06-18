package com.example.elancer.token.exception;

public class CustomRefreshJwtExpiredException extends RuntimeException {
    private static final String MESSAGE = "Refresh 토큰이 만료되었습니다.";

    public CustomRefreshJwtExpiredException() {
        super(MESSAGE);
    }
}
