package com.example.elancer.freelancerprofile.repository.position.etc;

import com.example.elancer.freelancerprofile.model.position.etc.PositionEtc;
import com.example.elancer.freelancerprofile.model.position.planner.Planner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PositionEtcRepository extends JpaRepository<PositionEtc, Long> {
    Optional<PositionEtc> findByFreelancerProfileNum(Long profileNum);
}
