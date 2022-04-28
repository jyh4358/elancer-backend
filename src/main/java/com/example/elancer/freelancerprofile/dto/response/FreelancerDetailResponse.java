package com.example.elancer.freelancerprofile.dto.response;

import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FreelancerDetailResponse {
    private Long profileNum;
    private String greeting;

    private PositionType positionType;
    private IntroduceResponse introduceResponse;
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
                // 일단 어떤 포지션인지 보내고 프론트에서 해당하는 api로 한번더 요청해 포지션에 맞는 응답을 받게 구현. 다만 뭔가 포지션 별로 응답 줄수 있을거 같은데... 이건 프론트분들과도 문서작성 부분에서 합의가 되야한다
                freelancerProfile.confirmPositionType(),
                IntroduceResponse.of(freelancerProfile),
                AcademicAbilityResponse.listOf(freelancerProfile.getAcademicAbilities()),
                CareerResponse.listOf(freelancerProfile.getCareers()),
                EducationResponse.listOf(freelancerProfile.getEducations()),
                LicenseResponse.listOf(freelancerProfile.getLicenses()),
                LanguageResponse.listOf(freelancerProfile.getLanguages()),
                ProjectHistoryResponse.listOf(freelancerProfile.getProjectHistories())
        );
    }
}
