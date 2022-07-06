package com.example.elancer.freelancerprofile.service.position;

import com.example.elancer.common.likechecker.FreelancerLikeChecker;
import com.example.elancer.freelancer.model.HopeWorkState;
import com.example.elancer.freelancerprofile.dto.FreelancerSimpleResponse;
import com.example.elancer.freelancerprofile.dto.FreelancerSimpleResponses;
import com.example.elancer.freelancerprofile.model.WorkArea;
import com.example.elancer.freelancerprofile.model.position.Position;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.freelancerprofile.model.position.PositionWorkManShip;
import com.example.elancer.freelancerprofile.model.position.developer.Developer;
import com.example.elancer.freelancerprofile.repository.DeveloperSearchRepository;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.wishfreelancer.repository.WishFreelancerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FreelancerPositionSearchService {
    private final DeveloperSearchRepository developerSearchRepository;
    private final WishFreelancerRepository wishFreelancerRepository;

    @Transactional(readOnly = true)
    public FreelancerSimpleResponses searchDevelopers(
            PositionType positionType,
            List<String> majorSkillKeywords,
            String minorSkill,
            List<HopeWorkState> hopeWorkStates,
            List<PositionWorkManShip> positionWorkManShips,
            WorkArea workArea,
            MemberDetails memberDetails
    ) {
        Slice<Developer> developers= developerSearchRepository.findFreelancerProfileByFetch(positionType, majorSkillKeywords, minorSkill, hopeWorkStates, positionWorkManShips, workArea);
        List<FreelancerSimpleResponse> freelancerSimpleResponses = developers.getContent().stream()
                .map(FreelancerSimpleResponse::of)
                .collect(Collectors.toList());
        FreelancerLikeChecker.confirmWishFreelancerToRequester(memberDetails, freelancerSimpleResponses, wishFreelancerRepository);

        return new FreelancerSimpleResponses(freelancerSimpleResponses);
    }

}
