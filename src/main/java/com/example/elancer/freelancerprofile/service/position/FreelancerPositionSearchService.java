package com.example.elancer.freelancerprofile.service.position;

import com.example.elancer.common.likechecker.FreelancerLikeChecker;
import com.example.elancer.common.utils.StringEditor;
import com.example.elancer.freelancer.model.HopeWorkState;
import com.example.elancer.freelancerprofile.dto.FreelancerSimpleResponse;
import com.example.elancer.freelancerprofile.dto.FreelancerSimpleResponses;
import com.example.elancer.freelancerprofile.model.WorkArea;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.freelancerprofile.model.position.PositionWorkManShip;
import com.example.elancer.freelancerprofile.model.position.designer.Designer;
import com.example.elancer.freelancerprofile.model.position.developer.Developer;
import com.example.elancer.freelancerprofile.model.position.etc.PositionEtc;
import com.example.elancer.freelancerprofile.model.position.planner.Planner;
import com.example.elancer.freelancerprofile.model.position.publisher.Publisher;
import com.example.elancer.freelancerprofile.repository.position.developer.DeveloperRepository;
import com.example.elancer.freelancerprofile.repository.positionsearch.DesignerSearchRepository;
import com.example.elancer.freelancerprofile.repository.positionsearch.DeveloperSearchRepository;
import com.example.elancer.freelancerprofile.repository.positionsearch.PlannerSearchRepository;
import com.example.elancer.freelancerprofile.repository.positionsearch.PositionEtcSearchRepository;
import com.example.elancer.freelancerprofile.repository.positionsearch.PublisherSearchRepository;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.wishfreelancer.repository.WishFreelancerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FreelancerPositionSearchService {
    private final DeveloperSearchRepository developerSearchRepository;
    private final PublisherSearchRepository publisherSearchRepository;
    private final DesignerSearchRepository designerSearchRepository;
    private final PlannerSearchRepository plannerSearchRepository;
    private final PositionEtcSearchRepository positionEtcSearchRepository;
    private final WishFreelancerRepository wishFreelancerRepository;

    @Transactional(readOnly = true)
    public FreelancerSimpleResponses searchDevelopers(
            PositionType positionType,
            String majorSkillKeywords,
            HopeWorkState hopeWorkState,
            PositionWorkManShip positionWorkManShip,
            WorkArea workArea,
            Pageable pageable,
            MemberDetails memberDetails
    ) {
        Slice<Developer> developers = developerSearchRepository.searchDevelopers(positionType, StringEditor.editStringToStringList(majorSkillKeywords), hopeWorkState, positionWorkManShip, pageable, workArea);
        List<FreelancerSimpleResponse> freelancerSimpleResponses = developers.getContent().stream()
                .map(FreelancerSimpleResponse::of)
                .collect(Collectors.toList());
        FreelancerLikeChecker.confirmWishFreelancerToRequester(memberDetails, freelancerSimpleResponses, wishFreelancerRepository);

        return new FreelancerSimpleResponses(freelancerSimpleResponses, developers.hasNext());
    }

    @Transactional(readOnly = true)
    public FreelancerSimpleResponses searchPublishers(
            PositionType positionType,
            String majorSkillKeywords,
            HopeWorkState hopeWorkState,
            PositionWorkManShip positionWorkManShip,
            WorkArea workArea,
            Pageable pageable,
            MemberDetails memberDetails
    ) {
        Slice<Publisher> publishers = publisherSearchRepository.searchPublishers(positionType, StringEditor.editStringToStringList(majorSkillKeywords), hopeWorkState, positionWorkManShip, workArea, pageable);
        List<FreelancerSimpleResponse> freelancerSimpleResponses = publishers.getContent().stream()
                .map(FreelancerSimpleResponse::of)
                .collect(Collectors.toList());
        FreelancerLikeChecker.confirmWishFreelancerToRequester(memberDetails, freelancerSimpleResponses, wishFreelancerRepository);

        return new FreelancerSimpleResponses(freelancerSimpleResponses, publishers.hasNext());
    }

    @Transactional(readOnly = true)
    public FreelancerSimpleResponses searchDesigners(
            PositionType positionType,
            String majorSkillKeywords,
            HopeWorkState hopeWorkState,
            PositionWorkManShip positionWorkManShip,
            WorkArea workArea,
            Pageable pageable,
            MemberDetails memberDetails
    ) {
        Slice<Designer> designers = designerSearchRepository.searchDesigners(positionType, StringEditor.editStringToStringList(majorSkillKeywords), hopeWorkState, positionWorkManShip, workArea, pageable);
        List<FreelancerSimpleResponse> freelancerSimpleResponses = designers.getContent().stream()
                .map(FreelancerSimpleResponse::of)
                .collect(Collectors.toList());
        FreelancerLikeChecker.confirmWishFreelancerToRequester(memberDetails, freelancerSimpleResponses, wishFreelancerRepository);

        return new FreelancerSimpleResponses(freelancerSimpleResponses, designers.hasNext());
    }

    @Transactional(readOnly = true)
    public FreelancerSimpleResponses searchPlanners(
            PositionType positionType,
            String majorSkillKeywords,
            HopeWorkState hopeWorkState,
            PositionWorkManShip positionWorkManShip,
            WorkArea workArea,
            Pageable pageable,
            MemberDetails memberDetails
    ) {
        Slice<Planner> planners = plannerSearchRepository.searchPlanners(positionType, StringEditor.editStringToStringList(majorSkillKeywords), hopeWorkState, positionWorkManShip, workArea, pageable);
        List<FreelancerSimpleResponse> freelancerSimpleResponses = planners.getContent().stream()
                .map(FreelancerSimpleResponse::of)
                .collect(Collectors.toList());
        FreelancerLikeChecker.confirmWishFreelancerToRequester(memberDetails, freelancerSimpleResponses, wishFreelancerRepository);

        return new FreelancerSimpleResponses(freelancerSimpleResponses, planners.hasNext());
    }

    @Transactional(readOnly = true)
    public FreelancerSimpleResponses searchPositionEtc(
            PositionType positionType,
            String majorSkillKeywords,
            HopeWorkState hopeWorkState,
            PositionWorkManShip positionWorkManShip,
            WorkArea workArea,
            Pageable pageable,
            MemberDetails memberDetails
    ) {
        Slice<PositionEtc> positionEtcs = positionEtcSearchRepository.searchPositionEtc(positionType, StringEditor.editStringToStringList(majorSkillKeywords), hopeWorkState, positionWorkManShip, workArea, pageable);
        List<FreelancerSimpleResponse> freelancerSimpleResponses = positionEtcs.getContent().stream()
                .map(FreelancerSimpleResponse::of)
                .collect(Collectors.toList());
        FreelancerLikeChecker.confirmWishFreelancerToRequester(memberDetails, freelancerSimpleResponses, wishFreelancerRepository);

        return new FreelancerSimpleResponses(freelancerSimpleResponses, positionEtcs.hasNext());
    }
}
