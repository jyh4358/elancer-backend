package com.example.elancer.applyproject.repository;

import com.example.elancer.applyproject.model.ApplyProject;
import com.example.elancer.interviewproject.model.InterviewProject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplyProjectRepository extends JpaRepository<ApplyProject, Long> {

    long countByProject_Num(Long projectNum);

    List<ApplyProject> findByProject_Num(Long projectNum);

    Optional<ApplyProject> findByProject_NumAndFreelancer_Num(Long projectNum, Long freelancerNum);
}
