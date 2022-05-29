package com.example.elancer.token.exception;

public class CustomJwtSignatureException extends RuntimeException {

    private static final String MESSAGE = "잘못된 토큰입니다.";

    public CustomJwtSignatureException() {
        super(MESSAGE);
    }
}
