package com.example.elancer.freelancerprofile.service;

import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.model.HopeWorkState;
import com.example.elancer.freelancerprofile.dto.FreelancerSimpleResponse;
import com.example.elancer.freelancerprofile.dto.FreelancerSimpleResponses;
import com.example.elancer.freelancerprofile.model.WorkArea;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.freelancerprofile.model.position.PositionWorkManShip;
import com.example.elancer.freelancerprofile.model.position.developer.Developer;
import com.example.elancer.freelancerprofile.repository.DeveloperSearchRepository;
import com.example.elancer.freelancerprofile.repository.position.developer.DeveloperRepository;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.member.domain.MemberType;
import com.example.elancer.wishfreelancer.model.WishFreelancer;
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
        List<FreelancerSimpleResponse> freelancerSimpleResponses = FreelancerSimpleResponse.listOf(developers.getContent());

        confirmWishFreelancerToRequester(memberDetails, freelancerSimpleResponses);

        return new FreelancerSimpleResponses(freelancerSimpleResponses);
    }

    private void confirmWishFreelancerToRequester(MemberDetails memberDetails, List<FreelancerSimpleResponse> freelancerSimpleResponses) {
        if (memberDetails != null && memberDetails.getRole().equals(MemberType.ENTERPRISE)) {
            List<WishFreelancer> wishFreelancersByEnterprise = wishFreelancerRepository.findByEnterpriseNum(memberDetails.getId());
            List<Long> wishFreelancerNums = wishFreelancersByEnterprise.stream()
                    .map(WishFreelancer::getFreelancer)
                    .map(Freelancer::getNum)
                    .collect(Collectors.toList());
            checkSearchResultInWishFreelancers(freelancerSimpleResponses, wishFreelancerNums);
        }
    }

    private void checkSearchResultInWishFreelancers(List<FreelancerSimpleResponse> freelancerSimpleResponses, List<Long> wishFreelancerNums) {
        for (FreelancerSimpleResponse freelancerSimpleResponse : freelancerSimpleResponses) {
            if (wishFreelancerNums.contains(freelancerSimpleResponse.getFreelancerNum())) {
                freelancerSimpleResponse.switchWishState();
            }
        }
    }
}
