package com.example.elancer.wishproject.exception;

public class NotExistWishProjectException extends RuntimeException {
    private static final String MESSAGE = "프로젝트찜 정보가 없습니다. 잘못된 요청입니다.";

    public NotExistWishProjectException() {
        super(MESSAGE);
    }
}
