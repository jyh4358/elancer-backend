package com.example.elancer.login.auth.exception;

public class GoogleSocialException extends RuntimeException {
    private static final String MESSAGE = "구글 유저 정보를 가져오지 못했습니다.";

    public GoogleSocialException() {
        super(MESSAGE);
    }
}
