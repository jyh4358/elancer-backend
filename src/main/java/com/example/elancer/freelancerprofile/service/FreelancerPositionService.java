package com.example.elancer.freelancerprofile.service;

import com.example.elancer.common.checker.RightRequesterChecker;
import com.example.elancer.freelancerprofile.dto.DesignerCoverRequest;
import com.example.elancer.freelancerprofile.dto.DeveloperCoverRequest;
import com.example.elancer.freelancerprofile.dto.PublisherCoverRequest;
import com.example.elancer.freelancerprofile.exception.NotExistFreelancerProfileException;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.position.designer.Designer;
import com.example.elancer.freelancerprofile.model.position.developer.Developer;
import com.example.elancer.freelancerprofile.model.position.publisher.Publisher;
import com.example.elancer.freelancerprofile.repository.FreelancerProfileRepository;
import com.example.elancer.freelancerprofile.repository.position.designer.DesignerRepository;
import com.example.elancer.freelancerprofile.repository.position.developer.DeveloperRepository;
import com.example.elancer.freelancerprofile.repository.position.publisher.PublisherRepository;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.querydsl.core.annotations.QueryTransient;
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

    /**
     * 1. 프리랜서에 등록되어있던 develop이 사라진다. -> developer db에서 num정보가 변경, 삭제된 developer와 연관된 값들도 변경되는지 확인해야한다.
     * 2. developer 인스턴스가 먼저 생성된다.
     * 3. 생성된 developer 인스턴스에 skillset을 추가하고 저장한다. -> 새로운 developer 생성되어 기존의 develop 데이터를 데체 and 기존 데이터 삭제.
     * */
    @Transactional
    public void coverFreelancerPositionToDeveloper(Long profileNum, MemberDetails memberDetails, DeveloperCoverRequest developerCoverRequest) {
        FreelancerProfile freelancerProfile = freelancerProfileRepository.findById(profileNum).orElseThrow(NotExistFreelancerProfileException::new);
        RightRequesterChecker.checkFreelancerProfileAndRequester(freelancerProfile, memberDetails);
        Developer developer = Developer.createBasicDeveloper(freelancerProfile, developerCoverRequest.getFocusSkill(), developerCoverRequest.getRole());
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
    public void coverFreelancerPositionToPublisher(Long profileNum, MemberDetails memberDetails, PublisherCoverRequest publisherCoverRequest) {
        FreelancerProfile freelancerProfile = freelancerProfileRepository.findById(profileNum).orElseThrow(NotExistFreelancerProfileException::new);
        RightRequesterChecker.checkFreelancerProfileAndRequester(freelancerProfile, memberDetails);
        Publisher publisher = Publisher.createBasicPublisher(freelancerProfile, publisherCoverRequest.getEtcSkill());
        publisher.coverPublishingSkill(publisherCoverRequest.toPublishingSkill(publisher));

        freelancerProfile.coverPosition(publisherRepository.save(publisher));
    }

    @Transactional
    public void coverFreelancerPositionToDesigner(Long profileNum, MemberDetails memberDetails, DesignerCoverRequest designerCoverRequest) {
        FreelancerProfile freelancerProfile = freelancerProfileRepository.findById(profileNum).orElseThrow(NotExistFreelancerProfileException::new);
        RightRequesterChecker.checkFreelancerProfileAndRequester(freelancerProfile, memberDetails);
        Designer designer = Designer.createBasicDesigner(freelancerProfile);
        designer.coverDesignRoleAndSkill(
                designerCoverRequest.toDesignRoles(designer),
                designerCoverRequest.toDesignSkills(designer),
                designerCoverRequest.getEtcRole(),
                designerCoverRequest.getEtcSkill()
        );

        freelancerProfile.coverPosition(designerRepository.save(designer));
    }
}
