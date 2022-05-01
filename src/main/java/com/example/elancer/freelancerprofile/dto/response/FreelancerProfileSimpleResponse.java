package com.example.elancer.freelancerprofile.dto.response;

import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.model.IntroBackGround;
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
public class FreelancerProfileSimpleResponse {
    private Long profileNum;
    private String name;
    private int expertise;
    private int scheduleAdherence;
    private int initiative;
    private int communication;
    private int reemploymentIntention;
    private Double totalActiveScore;

    private String introduceName;
    private IntroBackGround introBackGround;
    private String greeting;
    private int careerYear;

    private PositionType positionType;
    private String positionTypeDescription;

    private String introduceVideoUrl;
    private String introduceContent;

    private List<ProjectHistoryResponse> projectHistoryResponses;
    private List<AcademicAbilityResponse> academicAbilityResponses;
    private List<CareerResponse> careerResponses;
    private List<EducationResponse> educationResponses;
    private List<LicenseResponse> licenseResponses;
    private List<LanguageResponse> languageResponses;

    public static FreelancerProfileSimpleResponse of(Freelancer freelancer, FreelancerProfile freelancerProfile) {
        return new FreelancerProfileSimpleResponse(
                freelancerProfile.getNum(),
                freelancer.getName(),
                freelancerProfile.getWorkAssessment().getExpertise(),
                freelancerProfile.getWorkAssessment().getScheduleAdherence(),
                freelancerProfile.getWorkAssessment().getInitiative(),
                freelancerProfile.getWorkAssessment().getCommunication(),
                freelancerProfile.getWorkAssessment().getReEmploymentIntention(),
                freelancerProfile.getWorkAssessment().getTotalActiveScore(),
                freelancerProfile.getIntroduceName(),
                freelancerProfile.getIntroBackGround(),
                freelancerProfile.getGreeting(),
                freelancer.getFreelancerAccountInfo().getCareerYear(),
                freelancerProfile.confirmPositionType(),
                Optional.ofNullable(freelancerProfile.confirmPositionType()).map(PositionType::getDesc).orElse(null),
                freelancerProfile.getIntroduceVideoURL(),
                freelancerProfile.getIntroduceContent(),
                ProjectHistoryResponse.listOf(freelancerProfile.getProjectHistories()),
                AcademicAbilityResponse.listOf(freelancerProfile.getAcademicAbilities()),
                CareerResponse.listOf(freelancerProfile.getCareers()),
                EducationResponse.listOf(freelancerProfile.getEducations()),
                LicenseResponse.listOf(freelancerProfile.getLicenses()),
                LanguageResponse.listOf(freelancerProfile.getLanguages())
        );
    }
}
