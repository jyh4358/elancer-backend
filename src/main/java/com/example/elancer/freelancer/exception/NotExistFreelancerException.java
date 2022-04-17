package com.example.elancer.freelancer.exception;

public class NotExistFreelancerException extends RuntimeException{
    private static final String MESSAGE = "프리랜서가 존재하지 않습니다. 잘못된 요청입니다.";

    public NotExistFreelancerException() {
        super(MESSAGE);
    }
}
