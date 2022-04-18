package com.example.elancer.freelancerprofile.exception;

public class NotExistFreelancerProfileException extends RuntimeException{
    private static final String MESSAGE = "프리랜서의 프로필 정보가 없습니다. 잘못된 요청입니다.";

    public NotExistFreelancerProfileException() {
        super(MESSAGE);
    }
}
