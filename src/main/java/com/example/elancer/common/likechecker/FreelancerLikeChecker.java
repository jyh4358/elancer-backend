package com.example.elancer.common.likechecker;

import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancerprofile.dto.FreelancerSimpleResponse;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.member.domain.MemberType;
import com.example.elancer.wishfreelancer.model.WishFreelancer;
import com.example.elancer.wishfreelancer.repository.WishFreelancerRepository;

import java.util.List;
import java.util.stream.Collectors;

public class FreelancerLikeChecker {

    public static void confirmWishFreelancerToRequester(MemberDetails memberDetails, List<FreelancerSimpleResponse> freelancerSimpleResponses, WishFreelancerRepository wishFreelancerRepository) {
        if (memberDetails != null && memberDetails.getRole().equals(MemberType.ENTERPRISE)) {
            List<WishFreelancer> wishFreelancersByEnterprise = wishFreelancerRepository.findByEnterpriseNum(memberDetails.getId());
            List<Long> wishFreelancerNums = wishFreelancersByEnterprise.stream()
                    .map(WishFreelancer::getFreelancer)
                    .map(Freelancer::getNum)
                    .collect(Collectors.toList());
            checkSearchResultInWishFreelancers(freelancerSimpleResponses, wishFreelancerNums);
        }
    }

    private static void checkSearchResultInWishFreelancers(List<FreelancerSimpleResponse> freelancerSimpleResponses, List<Long> wishFreelancerNums) {
        for (FreelancerSimpleResponse freelancerSimpleResponse : freelancerSimpleResponses) {
            if (wishFreelancerNums.contains(freelancerSimpleResponse.getFreelancerNum())) {
                freelancerSimpleResponse.switchWishState();
            }
        }
    }
}
