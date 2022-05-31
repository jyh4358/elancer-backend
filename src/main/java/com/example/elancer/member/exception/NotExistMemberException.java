package com.example.elancer.member.exception;

public class NotExistMemberException extends RuntimeException{
    private static final String MESSAGE = "회원이 존재하지 않습니다.";

    public NotExistMemberException() {
        super(MESSAGE);
    }
}
