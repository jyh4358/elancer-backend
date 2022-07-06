package com.example.elancer.applyproject.repository;

import com.example.elancer.applyproject.model.ApplyProject;
import com.example.elancer.interviewproject.model.InterviewProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ApplyProjectRepository extends JpaRepository<ApplyProject, Long> {

    long countByProject_Num(Long projectNum);

    @Query("select count(ap.project.num) from ApplyProject ap where ap.project.num in :numList group by ap.project.num")
    List<Long> countByProject_NumGroupByProject_Num(@Param("numList") List<Long> numList);

    List<ApplyProject> findByProject_Num(Long projectNum);

    Optional<ApplyProject> findByProject_NumAndFreelancer_Num(Long projectNum, Long freelancerNum);

    List<ApplyProject> findByFreelancerNum(Long freelancerNum);

    void deleteAllByProject_Num(Long num);
}
