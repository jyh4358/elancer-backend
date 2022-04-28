package com.example.elancer.freelancerprofile.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FreelancerDetailResponse {
    private Long profileNum;
    private String greeting;

    private IntroduceResponse introduceResponse;
    private PositionResponse positionResponse;
    private List<AcademicAbilityResponse> academicAbilityResponses;
    private List<CareerResponse> careerResponses;
    private List<EducationResponse> educationResponses;
    private List<LicenseResponse> licenseResponses;
    private List<LanguageResponse> languageResponses;
    private List<ProjectHistoryResponse> projectHistoryResponses;
}
