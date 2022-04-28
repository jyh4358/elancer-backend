package com.example.elancer.freelancerprofile.service;

import com.example.elancer.freelancerprofile.dto.response.FreelancerDetailResponse;
import com.example.elancer.freelancerprofile.exception.NotExistFreelancerProfileException;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.repository.FreelancerProfileFindRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FreelancerProfileFindService {
    private final FreelancerProfileFindRepository freelancerProfileFindRepository;

    @Transactional
    public FreelancerDetailResponse findDetailFreelancerProfile(Long freelancerNum) {
        FreelancerProfile freelancerProfile = freelancerProfileFindRepository.findFreelancerProfileByFetch(freelancerNum).orElseThrow(NotExistFreelancerProfileException::new);

    }
}
