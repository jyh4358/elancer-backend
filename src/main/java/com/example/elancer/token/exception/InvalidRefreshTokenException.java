package com.example.elancer.token.exception;

public class InvalidRefreshTokenException extends RuntimeException {

    private static final String MESSAGE = "토큰이 만료되었습니다.";

    public InvalidRefreshTokenException() {
        super(MESSAGE);
    }
}
