package com.example.elancer.freelancer.repository;

import com.example.elancer.freelancer.model.WorkType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreelancerWorkTypeRepository extends JpaRepository<WorkType, Long> {
}
