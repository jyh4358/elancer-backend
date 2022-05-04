package com.example.elancer.freelancerprofile.exception;

public class NotExistDesignerException extends RuntimeException {
    private static final String MESSAGE = "해당 프리랜서는 디자이너 포지션이 아닙니다. 다시 확인해 주세요.";

    public NotExistDesignerException() {
        super(MESSAGE);
    }
}
