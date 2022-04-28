package com.example.elancer.freelancerprofile.service;

import com.example.elancer.freelancer.exception.NotExistFreelancerException;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.repository.FreelancerProfileFindRepository;
import com.example.elancer.freelancerprofile.repository.FreelancerProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FreelancerProfileFindService {
    private final FreelancerProfileRepository freelancerProfileRepository;
    private final FreelancerProfileFindRepository freelancerProfileFindRepository;

    @Transactional
    public void findDetailFreelancerProfile(Long freelancerNum) {
        FreelancerProfile freelancerProfile = freelancerProfileRepository.findByFreelancerNum(freelancerNum).orElseThrow(NotExistFreelancerException::new);
        freelancerProfileFindRepository.findFreelancerProfileByFetch(freelancerNum);
    }
}
