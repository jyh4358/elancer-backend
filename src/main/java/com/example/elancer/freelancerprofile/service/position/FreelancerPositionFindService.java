package com.example.elancer.freelancerprofile.service.position;

import com.example.elancer.common.checker.RightRequestChecker;
import com.example.elancer.freelancerprofile.dto.PublisherResponse;
import com.example.elancer.freelancerprofile.dto.DesignerResponse;
import com.example.elancer.freelancerprofile.dto.DeveloperResponse;
import com.example.elancer.freelancerprofile.dto.PlannerResponse;
import com.example.elancer.freelancerprofile.dto.PositionEtcResponse;
import com.example.elancer.freelancerprofile.exception.NotExistDesignerException;
import com.example.elancer.freelancerprofile.exception.NotExistDevelopException;
import com.example.elancer.freelancerprofile.exception.NotExistFreelancerProfileException;
import com.example.elancer.freelancerprofile.exception.NotExistPlannerException;
import com.example.elancer.freelancerprofile.exception.NotExistPositionEtcException;
import com.example.elancer.freelancerprofile.exception.NotExistPublisherException;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.position.designer.Designer;
import com.example.elancer.freelancerprofile.model.position.developer.Developer;
import com.example.elancer.freelancerprofile.model.position.etc.PositionEtc;
import com.example.elancer.freelancerprofile.model.position.planner.Planner;
import com.example.elancer.freelancerprofile.model.position.publisher.Publisher;
import com.example.elancer.freelancerprofile.repository.FreelancerProfileRepository;
import com.example.elancer.freelancerprofile.repository.position.designer.DesignerRepository;
import com.example.elancer.freelancerprofile.repository.position.developer.DeveloperRepository;
import com.example.elancer.freelancerprofile.repository.position.etc.PositionEtcRepository;
import com.example.elancer.freelancerprofile.repository.position.planner.PlannerRepository;
import com.example.elancer.freelancerprofile.repository.position.publisher.PublisherRepository;
import com.example.elancer.login.auth.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FreelancerPositionFindService {
    private final FreelancerProfileRepository freelancerProfileRepository;
    private final DeveloperRepository developerRepository;
    private final PublisherRepository publisherRepository;
    private final DesignerRepository designerRepository;
    private final PlannerRepository plannerRepository;
    private final PositionEtcRepository positionEtcRepository;

    @Transactional(readOnly = true)
    public DeveloperResponse findFreelancerPositionToDeveloper(MemberDetails memberDetails) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        FreelancerProfile freelancerProfile = freelancerProfileRepository.findByFreelancerNum(memberDetails.getId()).orElseThrow(NotExistFreelancerProfileException::new);
        RightRequestChecker.checkFreelancerProfileAndRequester(freelancerProfile, memberDetails);
        Developer developer = developerRepository.findByFreelancerProfileNum(freelancerProfile.getNum()).orElseThrow(NotExistDevelopException::new);
        return DeveloperResponse.of(developer);
    }

    @Transactional(readOnly = true)
    public PublisherResponse findFreelancerPositionToPublisher(MemberDetails memberDetails) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        FreelancerProfile freelancerProfile = freelancerProfileRepository.findByFreelancerNum(memberDetails.getId()).orElseThrow(NotExistFreelancerProfileException::new);
        RightRequestChecker.checkFreelancerProfileAndRequester(freelancerProfile, memberDetails);
        Publisher publisher = publisherRepository.findByFreelancerProfileNum(freelancerProfile.getNum()).orElseThrow(NotExistPublisherException::new);
        return PublisherResponse.of(publisher);
    }

    @Transactional(readOnly = true)
    public DesignerResponse findFreelancerPositionToDesigner(MemberDetails memberDetails) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        FreelancerProfile freelancerProfile = freelancerProfileRepository.findByFreelancerNum(memberDetails.getId()).orElseThrow(NotExistFreelancerProfileException::new);
        RightRequestChecker.checkFreelancerProfileAndRequester(freelancerProfile, memberDetails);
        Designer designer = designerRepository.findByFreelancerProfileNum(freelancerProfile.getNum()).orElseThrow(NotExistDesignerException::new);
        return DesignerResponse.of(designer);
    }

    @Transactional(readOnly = true)
    public PlannerResponse findFreelancerPositionToPlanner(MemberDetails memberDetails) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        FreelancerProfile freelancerProfile = freelancerProfileRepository.findByFreelancerNum(memberDetails.getId()).orElseThrow(NotExistFreelancerProfileException::new);
        RightRequestChecker.checkFreelancerProfileAndRequester(freelancerProfile, memberDetails);
        Planner planner = plannerRepository.findByFreelancerProfileNum(freelancerProfile.getNum()).orElseThrow(NotExistPlannerException::new);
        return PlannerResponse.of(planner);
    }

    @Transactional(readOnly = true)
    public PositionEtcResponse findFreelancerPositionToEtc(MemberDetails memberDetails) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        FreelancerProfile freelancerProfile = freelancerProfileRepository.findByFreelancerNum(memberDetails.getId()).orElseThrow(NotExistFreelancerProfileException::new);
        RightRequestChecker.checkFreelancerProfileAndRequester(freelancerProfile, memberDetails);
        PositionEtc positionEtc = positionEtcRepository.findByFreelancerProfileNum(freelancerProfile.getNum()).orElseThrow(NotExistPositionEtcException::new);
        return PositionEtcResponse.of(positionEtc);
    }
}
