package com.example.elancer.freelancerprofile.dto.response;

import com.example.elancer.common.model.WorkAssessment;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.model.FreelancerThumbnail;
import com.example.elancer.freelancer.model.IntroBackGround;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FreelancerProfileSimpleResponse {
    private Long profileNum;
    private String name;
    private String thumbnailPath;
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

    private List<String> allSkillNames;

    public static FreelancerProfileSimpleResponse of(FreelancerProfile freelancerProfile) {
        return new FreelancerProfileSimpleResponse(
                freelancerProfile.getNum(),
                freelancerProfile.getFreelancer().getName(),
                Optional.ofNullable(freelancerProfile.getFreelancer().getFreelancerThumbnail()).map(FreelancerThumbnail::getThumbnailPath).orElse(null),
                Optional.ofNullable(freelancerProfile.getFreelancer().getWorkAssessment()).map(WorkAssessment::getExpertise).orElse(null),
                Optional.ofNullable(freelancerProfile.getFreelancer().getWorkAssessment()).map(WorkAssessment::getScheduleAdherence).orElse(null),
                Optional.ofNullable(freelancerProfile.getFreelancer().getWorkAssessment()).map(WorkAssessment::getInitiative).orElse(null),
                Optional.ofNullable(freelancerProfile.getFreelancer().getWorkAssessment()).map(WorkAssessment::getCommunication).orElse(null),
                Optional.ofNullable(freelancerProfile.getFreelancer().getWorkAssessment()).map(WorkAssessment::getReEmploymentIntention).orElse(null),
                Optional.ofNullable(freelancerProfile.getFreelancer().getWorkAssessment()).map(WorkAssessment::getTotalActiveScore).orElse(null),
                freelancerProfile.getIntroduceName(),
                freelancerProfile.getIntroBackGround(),
                freelancerProfile.getGreeting(),
                freelancerProfile.getFreelancer().getFreelancerAccountInfo().getCareerYear(),
                freelancerProfile.confirmPositionType(),
                Optional.ofNullable(freelancerProfile.confirmPositionType()).map(PositionType::getDesc).orElse(null),
                freelancerProfile.getIntroduceVideoURL(),
                freelancerProfile.getIntroduceContent(),
                ProjectHistoryResponse.listOf(freelancerProfile.getProjectHistories()),
                AcademicAbilityResponse.listOf(freelancerProfile.getAcademicAbilities()),
                CareerResponse.listOf(freelancerProfile.getCareers()),
                EducationResponse.listOf(freelancerProfile.getEducations()),
                LicenseResponse.listOf(freelancerProfile.getLicenses()),
                LanguageResponse.listOf(freelancerProfile.getLanguages()),
                freelancerProfile.getPosition().getAllSkillNames()
        );
    }
}
