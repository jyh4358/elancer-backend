package com.example.elancer.freelancer.service;

import com.example.elancer.common.checker.RightRequestChecker;
import com.example.elancer.freelancer.dto.FreelancerAccountCoverRequest;
import com.example.elancer.freelancer.dto.FreelancerAccountDetailResponse;
import com.example.elancer.freelancer.exception.NotExistFreelancerException;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.repository.FreelancerRepository;
import com.example.elancer.freelancerprofile.repository.FreelancerProfileRepository;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.member.domain.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FreelancerService {
    private final FreelancerRepository freelancerRepository;
    private final FreelancerProfileRepository freelancerProfileRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void coverFreelancerAccountInfo(MemberDetails memberDetails, FreelancerAccountCoverRequest freelancerAccountCoverRequest) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        Freelancer freelancer = freelancerRepository.findById(memberDetails.getId()).orElseThrow(NotExistFreelancerException::new);
        RightRequestChecker.checkFreelancerAndRequester(freelancer, memberDetails);
        RightRequestChecker.checkPasswordMatch(freelancerAccountCoverRequest.getPassword(), freelancerAccountCoverRequest.getPasswordCheck());
        freelancer.updateFreelancer(
                freelancerAccountCoverRequest.getName(),
                passwordEncoder.encode(freelancerAccountCoverRequest.getPassword()),
                freelancerAccountCoverRequest.getEmail(),
                freelancerAccountCoverRequest.getPhone(),
                freelancerAccountCoverRequest.getWebsite(),
                new Address(
                        freelancerAccountCoverRequest.getCountryType(),
                        freelancerAccountCoverRequest.getZipcode(),
                        freelancerAccountCoverRequest.getMainAddress(),
                        freelancerAccountCoverRequest.getDetailAddress()
                ),
                freelancerAccountCoverRequest.getBirthDate(),
                freelancerAccountCoverRequest.getCareerYear(),
                freelancerAccountCoverRequest.getCareerMonth(),
                freelancerAccountCoverRequest.getHopeMonthMinPay(),
                freelancerAccountCoverRequest.getHopeMonthMaxPay(),
                freelancerAccountCoverRequest.toWorkTypes(),
                freelancerAccountCoverRequest.getWorkEtcField(),
                freelancerAccountCoverRequest.getKosaState(),
                freelancerAccountCoverRequest.getMailReceptionState(),
                freelancerAccountCoverRequest.getPresentWorkState(),
                freelancerAccountCoverRequest.getHopeWorkState(),
                freelancerAccountCoverRequest.getWorkPossibleState(),
                freelancerAccountCoverRequest.getWorkStartPossibleDate(),
                freelancerAccountCoverRequest.getHopeWorkCountry(),
                freelancerAccountCoverRequest.getHopeWorkCity()
        );
// TODO min 경력기술서 s3하고 진행해야한다.
//        freelancer.coverCareerForm(freelancerAccountCoverRequest.getCareerForm());

    }

    @Transactional(readOnly = true)
    public FreelancerAccountDetailResponse findDetailFreelancerAccount(Long freelancerNum, MemberDetails memberDetails) {
        Freelancer freelancer = freelancerRepository.findById(freelancerNum).orElseThrow(NotExistFreelancerException::new);
        RightRequestChecker.checkFreelancerAndRequester(freelancer, memberDetails);
        return FreelancerAccountDetailResponse.of(freelancer);
    }
}
