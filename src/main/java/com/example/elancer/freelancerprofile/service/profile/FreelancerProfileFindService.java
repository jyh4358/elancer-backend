package com.example.elancer.freelancerprofile.service.profile;

import com.example.elancer.common.checker.RightRequestChecker;
import com.example.elancer.common.exception.ImpossibleException;
import com.example.elancer.freelancer.exception.NotExistFreelancerException;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.repository.FreelancerRepository;
import com.example.elancer.freelancerprofile.dto.response.FreelancerDetailResponse;
import com.example.elancer.freelancerprofile.dto.response.FreelancerProfileSimpleResponse;
import com.example.elancer.freelancerprofile.exception.NotExistFreelancerProfileException;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.repository.FreelancerProfileFindRepository;
import com.example.elancer.freelancerprofile.repository.FreelancerProfileRepository;
import com.example.elancer.login.auth.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FreelancerProfileFindService {
    private final FreelancerProfileFindRepository freelancerProfileFindRepository;
    private final FreelancerProfileRepository freelancerProfileRepository;
    private final FreelancerRepository freelancerRepository;

    @Transactional(readOnly = true)
    public FreelancerDetailResponse findDetailFreelancerProfile(MemberDetails memberDetails) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        FreelancerProfile freelancerProfile = freelancerProfileFindRepository.findFreelancerProfileByFetch(memberDetails.getId()).orElseThrow(NotExistFreelancerProfileException::new);
        RightRequestChecker.checkFreelancerProfileAndRequester(freelancerProfile, memberDetails);
        return FreelancerDetailResponse.of(freelancerProfile);
    }

    @Transactional(readOnly = true)
    public FreelancerProfileSimpleResponse findSimpleFreelancerAccount(MemberDetails memberDetails) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        FreelancerProfile freelancerProfile = freelancerProfileRepository.findByFreelancerNum(memberDetails.getId()).orElseThrow(() -> new ImpossibleException("발생 불가능한 예외 입니다. 프리랜서와 프리랜서 프로필 디비 데이터를 확인해 주세요."));
        RightRequestChecker.checkFreelancerProfileAndRequester(freelancerProfile, memberDetails);
        return FreelancerProfileSimpleResponse.of(freelancerProfile);
    }
}
