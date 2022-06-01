package com.example.elancer.token.exception;

public class InvalidRefreshTokenException extends RuntimeException {

    private static final String MESSAGE = "refresh 토큰이 유효하지 않습니다.";

    public InvalidRefreshTokenException() {
        super(MESSAGE);
    }
}
