package com.example.elancer.freelancerprofile.service;

import com.example.elancer.common.checker.RightRequestChecker;
import com.example.elancer.freelancerprofile.dto.request.AcademicAbilityCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.AcademicAbilityCoverRequests;
import com.example.elancer.freelancerprofile.dto.request.CareerCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.CareerCoverRequests;
import com.example.elancer.freelancerprofile.dto.request.EducationAndLicenseAndLanguageRequests;
import com.example.elancer.freelancerprofile.dto.request.EducationCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.IntroduceCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.LanguageCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.LicenseCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.ProjectHistoryCoverRequest;
import com.example.elancer.freelancerprofile.exception.NotExistFreelancerProfileException;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.academic.AcademicAbility;
import com.example.elancer.freelancerprofile.model.career.Career;
import com.example.elancer.freelancerprofile.model.education.Education;
import com.example.elancer.freelancerprofile.model.language.Language;
import com.example.elancer.freelancerprofile.model.license.License;
import com.example.elancer.freelancerprofile.model.projecthistory.DevelopEnvironment;
import com.example.elancer.freelancerprofile.model.projecthistory.ProjectHistory;
import com.example.elancer.freelancerprofile.repository.FreelancerProfileRepository;
import com.example.elancer.login.auth.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FreelancerProfileAlterService {
    private final FreelancerProfileRepository freelancerProfileRepository;

    // TODO 저장 로직 쿼리 괴랄함. 기능구현 얼추 끝나면 좀 파야할 필요성 있음.
    @Transactional
    public void coverFreelancerIntroduce(MemberDetails memberDetails, IntroduceCoverRequest introduceCoverRequest) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        FreelancerProfile freelancerProfile = freelancerProfileRepository.findByFreelancerNum(memberDetails.getId()).orElseThrow(NotExistFreelancerProfileException::new);
        RightRequestChecker.checkFreelancerProfileAndRequester(freelancerProfile, memberDetails);
        freelancerProfile.coverIntroduceInFreelancer(
                introduceCoverRequest.getGreeting(),
                introduceCoverRequest.getIntroName(),
                introduceCoverRequest.getIntroBackGround(),
                introduceCoverRequest.getIntroVideoUrl(),
                introduceCoverRequest.getIntroContent()
        );
    }

    @Transactional
    public void coverFreelancerAcademicAbility(MemberDetails memberDetails, AcademicAbilityCoverRequests academicAbilityCoverRequests) {
        // min 쿼리 10번때림;; 쿼리 개선여부 확인후 성능개선해볼것.
        RightRequestChecker.checkMemberDetail(memberDetails);
        FreelancerProfile freelancerProfile = freelancerProfileRepository.findByFreelancerNum(memberDetails.getId()).orElseThrow(NotExistFreelancerProfileException::new);
        RightRequestChecker.checkFreelancerProfileAndRequester(freelancerProfile, memberDetails);
        List<AcademicAbility> academicAbilities = academicAbilityCoverRequests.getAcademicAbilityCoverRequests().stream()
                .map(AcademicAbilityCoverRequest::toAcademicAbility)
                .collect(Collectors.toList());

        freelancerProfile.coverAcademicAbilities(academicAbilities);
    }

    @Transactional
    public void coverFreelancerCareer(MemberDetails memberDetails, CareerCoverRequests careerCoverRequests) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        FreelancerProfile freelancerProfile = freelancerProfileRepository.findByFreelancerNum(memberDetails.getId()).orElseThrow(NotExistFreelancerProfileException::new);
        RightRequestChecker.checkFreelancerProfileAndRequester(freelancerProfile, memberDetails);
        List<Career> careers = careerCoverRequests.getCareerCoverRequests().stream()
                .map(CareerCoverRequest::toCareerEntity)
                .collect(Collectors.toList());

        freelancerProfile.coverCareers(careers);
    }

    @Transactional
    public void coverFreelancerProjectHistory(MemberDetails memberDetails, ProjectHistoryCoverRequest projectHistoryCoverRequest) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        FreelancerProfile freelancerProfile = freelancerProfileRepository.findByFreelancerNum(memberDetails.getId()).orElseThrow(NotExistFreelancerProfileException::new);
        RightRequestChecker.checkFreelancerProfileAndRequester(freelancerProfile, memberDetails);
        freelancerProfile.plusProjectHistory(ProjectHistory.createProjectHistory(
                projectHistoryCoverRequest.getProjectTitle(),
                projectHistoryCoverRequest.getProjectStartDate(),
                projectHistoryCoverRequest.getProjectEndDate(),
                projectHistoryCoverRequest.getClientCompany(),
                projectHistoryCoverRequest.getWorkCompany(),
                projectHistoryCoverRequest.getDevelopField(),
                projectHistoryCoverRequest.getDevelopRole(),
                DevelopEnvironment.of(
                        projectHistoryCoverRequest.getDevelopEnvironmentModel(),
                        projectHistoryCoverRequest.getDevelopEnvironmentOS(),
                        projectHistoryCoverRequest.getDevelopEnvironmentLanguage(),
                        projectHistoryCoverRequest.getDevelopEnvironmentDBName(),
                        projectHistoryCoverRequest.getDevelopEnvironmentTool(),
                        projectHistoryCoverRequest.getDevelopEnvironmentCommunication(),
                        projectHistoryCoverRequest.getDevelopEnvironmentEtc()
                ),
                projectHistoryCoverRequest.getResponsibilityTask()
        ));
    }

    @Transactional
    public void coverFreelancerEducationAndLicenseAndLanguage(MemberDetails memberDetails, EducationAndLicenseAndLanguageRequests educationAndLicenseAndLanguageRequests) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        FreelancerProfile freelancerProfile = freelancerProfileRepository.findByFreelancerNum(memberDetails.getId()).orElseThrow(NotExistFreelancerProfileException::new);
        RightRequestChecker.checkFreelancerProfileAndRequester(freelancerProfile, memberDetails);

        coverFreeLancerEducation(freelancerProfile, educationAndLicenseAndLanguageRequests.getEducationCoverRequests());
        coverFreeLancerLicense(freelancerProfile, educationAndLicenseAndLanguageRequests.getLicenseCoverRequests());
        coverFreeLancerLanguage(freelancerProfile, educationAndLicenseAndLanguageRequests.getLanguageCoverRequests());
    }

    @Transactional
    protected void coverFreeLancerEducation(FreelancerProfile freelancerProfile, List<EducationCoverRequest> educationCoverRequests) {
        List<Education> educations = educationCoverRequests.stream()
                .map(EducationCoverRequest::toEducation)
                .collect(Collectors.toList());
        freelancerProfile.coverEducation(educations);
    }

    @Transactional
    protected void coverFreeLancerLicense(FreelancerProfile freelancerProfile, List<LicenseCoverRequest> licenseCoverRequests) {
        List<License> licenses = licenseCoverRequests.stream()
                .map(LicenseCoverRequest::toLicense)
                .collect(Collectors.toList());
        freelancerProfile.coverLicense(licenses);
    }

    @Transactional
    protected void coverFreeLancerLanguage(FreelancerProfile freelancerProfile, List<LanguageCoverRequest> languageCoverRequests) {
        List<Language> languages = languageCoverRequests.stream()
                .map(LanguageCoverRequest::toLanguage)
                .collect(Collectors.toList());
        freelancerProfile.coverLanguage(languages);
    }
}
