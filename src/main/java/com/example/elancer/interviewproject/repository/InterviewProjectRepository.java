package com.example.elancer.interviewproject.repository;

import com.example.elancer.interviewproject.model.InterviewProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InterviewProjectRepository extends JpaRepository<InterviewProject, Long> {

    long countByProject_Num(Long projectNum);

    @Query("select count(ip.project.num) from InterviewProject ip where ip.project.num in :numList group by ip.project.num")
    List<Long> countInterviewProject(@Param("numList") List<Long> numList);

    List<InterviewProject> findByProject_Num(Long projectNum);

    Optional<InterviewProject> findByProject_NumAndFreelancer_Num(Long projectNum, Long freelancerNum);

    void deleteAllByProject_Num(Long num);
}
