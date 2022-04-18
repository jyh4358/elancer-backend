package com.example.elancer.enterprise.exception;

public class CheckPasswordException extends RuntimeException{
    private static final String MESSAGE = "비밀번호가 일치하지 않습니다.";

    public CheckPasswordException() {
        super(MESSAGE);
    }
}
