package com.example.elancer.token.exception;

public class CustomJwtMalformedException extends RuntimeException {

    private static final String MESSAGE = "변조된 토큰입니다.";

    public CustomJwtMalformedException() {
        super(MESSAGE);
    }
}
