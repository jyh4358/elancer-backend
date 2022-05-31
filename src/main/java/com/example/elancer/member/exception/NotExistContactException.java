package com.example.elancer.member.exception;

public class NotExistContactException extends RuntimeException{
    private static final String MESSAGE = "문의글이 존재하지 않습니다.";

    public NotExistContactException() {
        super(MESSAGE);
    }
}
