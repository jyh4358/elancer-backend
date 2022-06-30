package com.example.elancer.freelancerprofile.service.position;

import com.example.elancer.common.checker.RightRequestChecker;
import com.example.elancer.common.utils.StringEditor;
import com.example.elancer.freelancerprofile.dto.request.position.DesignerCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.position.DeveloperCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.position.PlannerCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.position.PositionEtcCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.position.PublisherCoverRequest;
import com.example.elancer.freelancerprofile.exception.NotExistFreelancerProfileException;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.position.CrowdWorker;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.freelancerprofile.model.position.designer.Designer;
import com.example.elancer.freelancerprofile.model.position.developer.Developer;
import com.example.elancer.freelancerprofile.model.position.etc.PositionEtc;
import com.example.elancer.freelancerprofile.model.position.planner.Planner;
import com.example.elancer.freelancerprofile.model.position.publisher.Publisher;
import com.example.elancer.freelancerprofile.repository.FreelancerProfileRepository;
import com.example.elancer.freelancerprofile.repository.position.CrowdWorkerRepository;
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
public class FreelancerPositionService {
    private final FreelancerProfileRepository freelancerProfileRepository;
    private final DeveloperRepository developerRepository;
    private final PublisherRepository publisherRepository;
    private final DesignerRepository designerRepository;
    private final PlannerRepository plannerRepository;
    private final CrowdWorkerRepository crowdWorkerRepository;
    private final PositionEtcRepository positionEtcRepository;

    /**
     * 1. 프리랜서에 등록되어있던 develop이 사라진다. -> developer db에서 num정보가 변경, 삭제된 developer와 연관된 값들도 변경되는지 확인해야한다.
     * 2. developer 인스턴스가 먼저 생성된다.
     * 3. 생성된 developer 인스턴스에 skillset을 추가하고 저장한다. -> 새로운 developer 생성되어 기존의 develop 데이터를 데체 and 기존 데이터 삭제.
     * !!! 밑처럼 불완전한 Developer객체를 생성하고 coverDeveloperSkills메서드를 통해 값을 넣어주면 객체의 생성 자체가 불완전하게 되는 단점이 있다. 해서 정적 팩토리 메서드에서 coverDeveloperSkills를 실행하는 방식으로 구현할까
     * 했는데.. 만약 coverDeveloperSkills방식이 변경되면 Developer객체 생성 로직 자체가 변할수도 있고 이 변경은 Developer객체가 생성되는 모든곳에 영향을 주기에 당장은 현상유지로 결정.
     **/
    @Transactional
    public void coverFreelancerPositionToDeveloper(MemberDetails memberDetails, DeveloperCoverRequest developerCoverRequest) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        FreelancerProfile freelancerProfile = freelancerProfileRepository.findByFreelancerNum(memberDetails.getId()).orElseThrow(NotExistFreelancerProfileException::new);
        RightRequestChecker.checkFreelancerProfileAndRequester(freelancerProfile, memberDetails);
        Developer developer = Developer.createBasicDeveloper(
                PositionType.DEVELOPER, freelancerProfile,
                StringEditor.editStringListToString(developerCoverRequest.getFocusSkills()),
                StringEditor.editStringListToString(developerCoverRequest.getRoles())
        );

        developer.coverDeveloperSkills(
                developerCoverRequest.toJavaSkill(developer),
                developerCoverRequest.toMobileAppSkill(developer),
                developerCoverRequest.toPhpOrAspSkill(developer),
                developerCoverRequest.toDotNetSkill(developer),
                developerCoverRequest.toJavaScriptSkill(developer),
                developerCoverRequest.toCSkill(developer),
                developerCoverRequest.toDBSkill(developer),
                developerCoverRequest.getEtcSkill()
        );
        freelancerProfile.coverPosition(developerRepository.save(developer));
    }

    @Transactional
    public void coverFreelancerPositionToPublisher(MemberDetails memberDetails, PublisherCoverRequest publisherCoverRequest) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        FreelancerProfile freelancerProfile = freelancerProfileRepository.findByFreelancerNum(memberDetails.getId()).orElseThrow(NotExistFreelancerProfileException::new);
        RightRequestChecker.checkFreelancerProfileAndRequester(freelancerProfile, memberDetails);
        Publisher publisher = Publisher.createBasicPublisher(PositionType.PUBLISHER, freelancerProfile, publisherCoverRequest.getEtcSkill());
        publisher.coverPublishingSkill(publisherCoverRequest.toPublishingSkill(publisher));

        freelancerProfile.coverPosition(publisherRepository.save(publisher));
    }

    @Transactional
    public void coverFreelancerPositionToDesigner(MemberDetails memberDetails, DesignerCoverRequest designerCoverRequest) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        FreelancerProfile freelancerProfile = freelancerProfileRepository.findByFreelancerNum(memberDetails.getId()).orElseThrow(NotExistFreelancerProfileException::new);
        RightRequestChecker.checkFreelancerProfileAndRequester(freelancerProfile, memberDetails);
        Designer designer = Designer.createBasicDesigner(PositionType.DESIGNER, freelancerProfile);
        designer.coverDesignRoleAndSkill(
                designerCoverRequest.toDesignRoles(designer),
                designerCoverRequest.toDesignSkills(designer),
                designerCoverRequest.getEtcRole(),
                designerCoverRequest.getEtcSkill()
        );

        freelancerProfile.coverPosition(designerRepository.save(designer));
    }

    @Transactional
    public void coverFreelancerPositionToPlanner(MemberDetails memberDetails, PlannerCoverRequest plannerCoverRequest) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        FreelancerProfile freelancerProfile = freelancerProfileRepository.findByFreelancerNum(memberDetails.getId()).orElseThrow(NotExistFreelancerProfileException::new);
        RightRequestChecker.checkFreelancerProfileAndRequester(freelancerProfile, memberDetails);
        Planner planner = Planner.createBasicPlanner(PositionType.PLANNER, freelancerProfile);
        planner.coverAllField(plannerCoverRequest.toPlannerField(planner), plannerCoverRequest.getEtcField());

        freelancerProfile.coverPosition(plannerRepository.save(planner));
    }

    @Transactional
    public void coverFreelancerPositionToCrowdWorker(MemberDetails memberDetails) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        FreelancerProfile freelancerProfile = freelancerProfileRepository.findByFreelancerNum(memberDetails.getId()).orElseThrow(NotExistFreelancerProfileException::new);
        RightRequestChecker.checkFreelancerProfileAndRequester(freelancerProfile, memberDetails);
        CrowdWorker crowdWorker = new CrowdWorker(PositionType.CROWD_WORKER, freelancerProfile);

        freelancerProfile.coverPosition(crowdWorkerRepository.save(crowdWorker));
    }

    @Transactional
    public void coverFreelancerPositionToEtc(MemberDetails memberDetails, PositionEtcCoverRequest positionEtcCoverRequest) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        FreelancerProfile freelancerProfile = freelancerProfileRepository.findByFreelancerNum(memberDetails.getId()).orElseThrow(NotExistFreelancerProfileException::new);
        RightRequestChecker.checkFreelancerProfileAndRequester(freelancerProfile, memberDetails);
        PositionEtc positionEtc = PositionEtc.createBasicPositionEtc(PositionType.ETC, freelancerProfile);
        positionEtc.coverAllField(positionEtcCoverRequest.toEtcRole(positionEtc), positionEtcCoverRequest.getPositionEtcRole());

        freelancerProfile.coverPosition(positionEtcRepository.save(positionEtc));
    }
}
