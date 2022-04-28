package com.example.elancer.freelancerprofile.repository;

import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FreelancerProfileRepository extends JpaRepository<FreelancerProfile, Long> {
    Optional<FreelancerProfile> findByFreelancerNum(Long freelancerNum);
}
