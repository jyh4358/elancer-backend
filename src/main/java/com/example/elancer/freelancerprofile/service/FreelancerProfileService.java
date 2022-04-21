package com.example.elancer.freelancerprofile.service;

import com.example.elancer.common.checker.RightRequesterChecker;
import com.example.elancer.freelancerprofile.dto.AcademicAbilityCoverRequest;
import com.example.elancer.freelancerprofile.dto.AcademicAbilityCoverRequests;
import com.example.elancer.freelancerprofile.dto.CareerCoverRequest;
import com.example.elancer.freelancerprofile.dto.CareerCoverRequests;
import com.example.elancer.freelancerprofile.dto.IntroduceCoverRequest;
import com.example.elancer.freelancerprofile.dto.ProjectHistoryCoverRequest;
import com.example.elancer.freelancerprofile.exception.NotExistFreelancerProfileException;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.academic.AcademicAbility;
import com.example.elancer.freelancerprofile.model.career.Career;
import com.example.elancer.freelancerprofile.model.projecthistory.DevelopEnvironment;
import com.example.elancer.freelancerprofile.model.projecthistory.ProjectHistory;
import com.example.elancer.freelancerprofile.repository.FreelancerProfileRepository;
import com.example.elancer.freelancerprofile.repository.academic.AcademicRepository;
import com.example.elancer.login.auth.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FreelancerProfileService {
    private final FreelancerProfileRepository freelancerProfileRepository;
    private final AcademicRepository academicRepository;

    @Transactional
    public void coverFreelancerIntroduce(MemberDetails memberDetails, Long profileNum, IntroduceCoverRequest introduceCoverRequest) {
        FreelancerProfile freelancerProfile = freelancerProfileRepository.findById(profileNum).orElseThrow(NotExistFreelancerProfileException::new);
        RightRequesterChecker.checkFreelancerProfileAndRequester(freelancerProfile, memberDetails);
        freelancerProfile.coverIntroduceInFreelancer(
                introduceCoverRequest.getIntroName(),
                introduceCoverRequest.getIntroBackGround(),
                introduceCoverRequest.getIntroVideoUrl(),
                introduceCoverRequest.getIntroContent()
        );
    }

    @Transactional
    public void coverFreelancerAcademicAbility(MemberDetails memberDetails, Long profileNum, AcademicAbilityCoverRequests academicAbilityCoverRequests) {
        // min 쿼리 10번때림;; 쿼리 개선여부 확인후 성능개선해볼것.
        FreelancerProfile freelancerProfile = freelancerProfileRepository.findById(profileNum).orElseThrow(NotExistFreelancerProfileException::new);
        RightRequesterChecker.checkFreelancerProfileAndRequester(freelancerProfile, memberDetails);
        List<AcademicAbility> academicAbilities = academicAbilityCoverRequests.getAcademicAbilityCoverRequests().stream()
                .map(AcademicAbilityCoverRequest::toAcademicAbility)
                .collect(Collectors.toList());

        freelancerProfile.coverAcademicAbilities(academicAbilities);
    }

    @Transactional
    public void coverFreelancerCareer(MemberDetails memberDetails, Long profileNum, CareerCoverRequests careerCoverRequests) {
        FreelancerProfile freelancerProfile = freelancerProfileRepository.findById(profileNum).orElseThrow(NotExistFreelancerProfileException::new);
        RightRequesterChecker.checkFreelancerProfileAndRequester(freelancerProfile, memberDetails);
        List<Career> careers = careerCoverRequests.getCareerCoverRequests().stream()
                .map(CareerCoverRequest::toCareerEntity)
                .collect(Collectors.toList());

        freelancerProfile.coverCareers(careers);
    }


    @Transactional
    public void coverFreelancerProjectHistory(MemberDetails memberDetails, Long profileNum, ProjectHistoryCoverRequest projectHistoryCoverRequest) {
        FreelancerProfile freelancerProfile = freelancerProfileRepository.findById(profileNum).orElseThrow(NotExistFreelancerProfileException::new);
        RightRequesterChecker.checkFreelancerProfileAndRequester(freelancerProfile, memberDetails);
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
}
