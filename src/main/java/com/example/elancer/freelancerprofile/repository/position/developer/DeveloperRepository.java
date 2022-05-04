package com.example.elancer.freelancerprofile.repository.position.developer;

import com.example.elancer.freelancerprofile.model.position.developer.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {
    Optional<Developer> findByFreelancerProfileNum(Long profileNum);
}
