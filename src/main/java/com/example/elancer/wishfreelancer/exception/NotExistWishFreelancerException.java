package com.example.elancer.wishfreelancer.exception;

public class NotExistWishFreelancerException extends RuntimeException {
    private static final String MESSAGE = "프리랜서 스크랩 정보가 없습니다. 잘못된 요청입니다.";

    public NotExistWishFreelancerException() {
        super(MESSAGE);
    }
}
