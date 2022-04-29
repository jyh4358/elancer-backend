package com.example.elancer.freelancerprofile.dto.response;

import com.example.elancer.freelancerprofile.model.career.Career;
import com.example.elancer.freelancerprofile.model.career.CompanyPosition;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CareerResponse {
    private String companyName;
    private String departmentName;
    private CompanyPosition companyPosition;
    private LocalDate careerStartDate;
    private LocalDate careerEndDate;

    public static CareerResponse of(Career career) {
        return new CareerResponse(
                career.getCompanyName(),
                career.getDepartmentName(),
                career.getCompanyPosition(),
                career.getCareerStartDate(),
                career.getCareerEndDate()
        );
    }

    public static List<CareerResponse> listOf(List<Career> careerList) {
        return careerList.stream()
                .map(CareerResponse::of)
                .collect(Collectors.toList());
    }
}
