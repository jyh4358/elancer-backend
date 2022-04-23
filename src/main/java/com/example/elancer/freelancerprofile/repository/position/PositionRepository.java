package com.example.elancer.freelancerprofile.repository.position;

import com.example.elancer.freelancerprofile.model.position.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long> {
}
