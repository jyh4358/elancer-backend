package com.example.elancer.common.checker;

import com.example.elancer.common.exception.WrongRequestException;
import com.example.elancer.freelancer.join.exception.FreelancerCheckPasswordException;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.login.auth.dto.MemberDetails;

public class RightRequesterChecker {

    public static void checkFreelancerAndRequester(Freelancer freelancer, MemberDetails memberDetails) {
        // min 로그인 기능 구현전 임시 코드
        if (memberDetails == null || !memberDetails.checkPresentId()) {
            return;
        }

        if (!memberDetails.getId().equals(freelancer.getUserId())) {
            throw new WrongRequestException("요청자와 조회된 프리랜서 정보가 틀립니다. 잘못된 요청입니다.");
        }
    }

    public static void checkFreelancerProfileAndRequester(FreelancerProfile freelancerProfile, MemberDetails memberDetails) {
        // min 로그인 기능 구현전 임시 코드
        if (memberDetails == null || !memberDetails.checkPresentId()) {
            return;
        }

        if (memberDetails.checkPresentId()) {
            freelancerProfile.checkFreelancerAndProfileMatcher(memberDetails.getUserId());
        }
    }

    public static void checkPasswordMatch(String password, String passwordCheck) {
        if (!password.equals(passwordCheck)) {
            throw new FreelancerCheckPasswordException();
        }
    }
}
