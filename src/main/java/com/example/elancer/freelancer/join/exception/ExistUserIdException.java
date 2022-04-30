package com.example.elancer.freelancer.join.exception;

public class ExistUserIdException extends RuntimeException {
    private static final String MESSAGE = "이미 존재하는 id 입니다. 다른 id로 가입해주세요.";

    public ExistUserIdException() {
        super(MESSAGE);
    }
}
