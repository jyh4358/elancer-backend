package com.example.elancer.common.checker;

import com.example.elancer.common.exception.WrongRequestException;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.login.auth.dto.MemberDetails;

public class RightRequesterChecker {

    public static void checkFreelancerAndRequester(Freelancer freelancer, MemberDetails memberDetails) {
        if (!memberDetails.checkPresentId() || freelancer == null) {
            return;
        }

        if (!memberDetails.getId().equals(freelancer.getUserId())) {
            throw new WrongRequestException("요청자와 조회된 프리랜서 정보가 틀립니다. 잘못된 요청입니다.");
        }
    }

    public static void checkFreelancerProfileAndRequester(FreelancerProfile freelancerProfile, MemberDetails memberDetails) {
        if (memberDetails.checkPresentId()) {
            freelancerProfile.checkFreelancerAndProfileMatcher(memberDetails.getUserId());
        }
    }
}
