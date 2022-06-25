package com.example.elancer.project.dto;

import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.interviewproject.model.InterviewStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InterviewRequestDto {
    private Long num;
    private String name;
    private String phone;
    private InterviewStatus interviewStatus;
    private int careerYear;
    private PositionType positionType;

    public InterviewRequestDto(Long num, String name, String phone, InterviewStatus interviewStatus) {
        this.num = num;
        this.name = name;
        this.phone = phone;
        this.interviewStatus = interviewStatus;
    }

    public InterviewRequestDto(Long num, String name, String phone, InterviewStatus interviewStatus, int careerYear, PositionType positionType) {
        this.num = num;
        this.name = name;
        this.phone = phone;
        this.interviewStatus = interviewStatus;
        this.careerYear = careerYear;
        this.positionType = positionType;
    }

    public static InterviewRequestDto of(Freelancer freelancer, InterviewStatus interviewStatus) {
        return new InterviewRequestDto(
                freelancer.getNum(),
                freelancer.getName(),
                freelancer.getPhone(),
                interviewStatus,
                freelancer.getFreelancerAccountInfo().getCareerYear(),
                freelancer.getFreelancerProfile().getPosition().getPositionType());
    }
}
