package com.example.elancer.freelancerprofile.dto.response;

import com.example.elancer.freelancerprofile.model.FreelancerProfile;
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

    public static FreelancerDetailResponse of(FreelancerProfile freelancerProfile) {
        return new FreelancerDetailResponse(
                freelancerProfile.getNum(),
                freelancerProfile.getGreeting(),
                IntroduceResponse.of(freelancerProfile),
                null,
                AcademicAbilityResponse.listOf(freelancerProfile.getAcademicAbilities()),
                CareerResponse.listOf(freelancerProfile.getCareers()),
                EducationResponse.listOf(freelancerProfile.getEducations()),
                LicenseResponse.listOf(freelancerProfile.getLicenses()),
                LanguageResponse.listOf(freelancerProfile.getLanguages()),
                ProjectHistoryResponse.listOf(freelancerProfile.getProjectHistories())
        );
    }
}
