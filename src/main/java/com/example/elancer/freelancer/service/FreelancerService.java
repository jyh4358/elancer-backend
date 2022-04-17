package com.example.elancer.freelancer.service;

import com.example.elancer.common.checker.RightRequesterChecker;
import com.example.elancer.freelancer.dto.FreelancerIntroSaveOrUpdateRequest;
import com.example.elancer.freelancer.exception.NotExistFreelancerException;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.repository.FreelancerRepository;
import com.example.elancer.login.auth.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FreelancerService {
    private final FreelancerRepository freelancerRepository;

    @Transactional
    public void saveOrUpdateFreelancerIntro(MemberDetails memberDetails, Long freelancerNum, FreelancerIntroSaveOrUpdateRequest freelancerIntroSaveOrUpdateRequest) {
        Freelancer freelancer = freelancerRepository.findById(freelancerNum).orElseThrow(NotExistFreelancerException::new);
        RightRequesterChecker.checkFreelancerAndRequester(freelancer, memberDetails);

//        freelancer.coverIntroduceInFreelancer(freelancerIntroSaveOrUpdateRequest.getIntroName());

    }
}
