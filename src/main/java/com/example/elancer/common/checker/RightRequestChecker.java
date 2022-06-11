package com.example.elancer.common.checker;

import com.example.elancer.common.exception.ImpossibleException;
import com.example.elancer.common.exception.WrongRequestException;
import com.example.elancer.enterprise.exception.EnterpriseCheckPasswordException;
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
        if (memberDetails.checkPresentId() && !freelancerProfile.getFreelancer().getNum().equals(memberDetails.getId())) {
            throw new WrongRequestException("프로필에 대한 요청자와 프리랜서가 동일하지 않습니다. 잘못된 요청입니다.");
        }
    }

    public static void checkPasswordMatch(String password, String passwordCheck) {
        if (!password.equals(passwordCheck)) {
            throw new FreelancerCheckPasswordException();
        }
    }

    public static void checkPasswordMatchEnterprise(String password, String passwordCheck) {
        if (!password.equals(passwordCheck)) {
            throw new EnterpriseCheckPasswordException();
        }
    }

    public static void checkMemberDetail(MemberDetails memberDetails) {
        if (memberDetails == null /*|| !memberDetails.checkPresentId()*/) {
            throw new ImpossibleException("로그인 되지 않은, 권한없는 사용자의 요청입니다. 요청을 다시 확인해 주세요.");
        }
    }


}
