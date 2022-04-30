package com.example.elancer.freelancer.service;

import com.example.elancer.common.checker.RightRequesterChecker;
import com.example.elancer.freelancer.dto.FreelancerAccountCoverRequest;
import com.example.elancer.freelancer.exception.NotExistFreelancerException;
import com.example.elancer.freelancer.model.CareerForm;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.repository.FreelancerRepository;
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
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void coverFreelancerAccountInfo(Long freelancerNum, MemberDetails memberDetails, FreelancerAccountCoverRequest freelancerAccountCoverRequest) {
        Freelancer freelancer = freelancerRepository.findById(freelancerNum).orElseThrow(NotExistFreelancerException::new);
        RightRequesterChecker.checkFreelancerAndRequester(freelancer, memberDetails);
        RightRequesterChecker.checkPasswordMatch(freelancerAccountCoverRequest.getPassword(), freelancerAccountCoverRequest.getPasswordCheck());
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

//        freelancer.coverCareerForm(freelancerAccountCoverRequest.getCareerForm());

    }
}
