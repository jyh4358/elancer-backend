package com.example.elancer.freelancerprofile.dto.request;

import com.example.elancer.freelancerprofile.model.career.Career;
import com.example.elancer.freelancerprofile.model.career.CompanyPosition;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CareerCoverRequest {
    private String companyName;
    private String departmentName;
    private CompanyPosition companyPosition;
    private LocalDate careerStartDate;
    private LocalDate careerEndDate;

    public static Career toCareerEntity(CareerCoverRequest careerCoverRequest) {
        return Career.createCareer(
                careerCoverRequest.getCompanyName(),
                careerCoverRequest.getDepartmentName(),
                careerCoverRequest.getCompanyPosition(),
                careerCoverRequest.getCareerStartDate(),
                careerCoverRequest.getCareerEndDate()
        );
    }
}
