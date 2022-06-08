package com.example.elancer.contact.exception;

public class NotExistContactException extends RuntimeException{
    private static final String MESSAGE = "해당 문의글이 존재하지 않습니다.";

    public NotExistContactException() {
        super(MESSAGE);
    }
}
