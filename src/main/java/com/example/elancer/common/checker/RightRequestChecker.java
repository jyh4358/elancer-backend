package com.example.elancer.common.checker;

import com.example.elancer.common.exception.ImpossibleException;
import com.example.elancer.common.exception.WrongRequestException;
import com.example.elancer.freelancer.join.exception.FreelancerCheckPasswordException;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.login.auth.dto.MemberDetails;

public class RightRequestChecker {

    public static void checkFreelancerAndRequester(Freelancer freelancer, MemberDetails memberDetails) {
        if (!memberDetails.getId().equals(freelancer.getNum())) {
            throw new WrongRequestException("요청자와 조회된 프리랜서 정보가 틀립니다. 잘못된 요청입니다.");
        }
    }

    public static void checkFreelancerProfileAndRequester(FreelancerProfile freelancerProfile, MemberDetails memberDetails) {
        if (memberDetails.checkPresentId() && !freelancerProfile.getNum().equals(memberDetails.getId())) {
            throw new WrongRequestException("프로필에 대한 요청자와 프리랜서가 동일하지 않습니다. 잘못된 요청입니다.");
        }
    }

    public static void checkPasswordMatch(String password, String passwordCheck) {
        if (!password.equals(passwordCheck)) {
            throw new FreelancerCheckPasswordException();
        }
    }

    public static void checkMemberDetail(MemberDetails memberDetails) {
        if (memberDetails == null) {
            throw new ImpossibleException("요청자가 없습니다. 잘못된 요청입니다.");
        }
    }


}
