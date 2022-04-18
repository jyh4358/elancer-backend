package com.example.elancer.freelancerprofile.repository;

import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreelancerProfileRepository extends JpaRepository<FreelancerProfile, Long> {
}
