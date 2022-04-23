package com.example.elancer.freelancerprofile.repository.position.developer;

import com.example.elancer.freelancerprofile.model.position.developer.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {
}
