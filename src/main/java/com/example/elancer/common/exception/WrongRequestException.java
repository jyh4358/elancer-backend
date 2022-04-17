package com.example.elancer.common.exception;

public class WrongRequestException extends RuntimeException {
    public WrongRequestException(String message) {
        super(message);
    }
}
