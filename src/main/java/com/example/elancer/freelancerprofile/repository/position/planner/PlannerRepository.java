package com.example.elancer.freelancerprofile.repository.position.planner;

import com.example.elancer.freelancerprofile.model.position.designer.Designer;
import com.example.elancer.freelancerprofile.model.position.planner.Planner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlannerRepository extends JpaRepository<Planner, Long> {
    Optional<Planner> findByFreelancerProfileNum(Long profileNum);
}
