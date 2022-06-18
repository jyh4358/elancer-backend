package com.example.elancer.token.exception;

public class CustomJwtExpiredException extends RuntimeException {

    private static final String MESSAGE = "Access 토큰이 만료되었습니다.";

    public CustomJwtExpiredException() {
        super(MESSAGE);
    }
}
