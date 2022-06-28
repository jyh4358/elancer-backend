package com.example.elancer.project.dto;

import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProcessingFreelancerDto {
    private Long num;
    private String name;
    private String phone;
    private int careerYear;
    private PositionType positionType;

    public ProcessingFreelancerDto(Long num, String name, String phone, int careerYear, PositionType positionType) {
        this.num = num;
        this.name = name;
        this.phone = phone;
        this.careerYear = careerYear;
        this.positionType = positionType;
    }

    public static ProcessingFreelancerDto of(Freelancer freelancer) {
        return new ProcessingFreelancerDto(
                freelancer.getNum(),
                freelancer.getName(),
                freelancer.getPhone(),
                freelancer.getFreelancerAccountInfo().getCareerYear(),
                freelancer.getFreelancerProfile().getPosition().getPositionType());
    }
}
