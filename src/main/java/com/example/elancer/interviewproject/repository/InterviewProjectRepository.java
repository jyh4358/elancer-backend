package com.example.elancer.interviewproject.repository;

import com.example.elancer.interviewproject.model.InterviewProject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterviewProjectRepository extends JpaRepository<InterviewProject, Long> {

    long countByProject_Num(Long projectNum);

    List<InterviewProject> findByProject_Num(Long projectNum);

    Optional<InterviewProject> findByProject_NumAndFreelancer_Num(Long projectNum, Long freelancerNum);
}
