package com.example.elancer.freelancerprofile.repository.career;

import com.example.elancer.freelancerprofile.model.career.Career;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CareerRepository extends JpaRepository<Career, Long> {
}
