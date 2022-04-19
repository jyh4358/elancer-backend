package com.example.elancer.login.auth.exception;

public class MemberDuplicateException extends RuntimeException{
    private static final String MESSAGE = "소셜 회원 가입을 하지 않았습니다.";

    public MemberDuplicateException() {
        super(MESSAGE);
    }
}
