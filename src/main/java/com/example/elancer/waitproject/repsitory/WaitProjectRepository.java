package com.example.elancer.waitproject.repsitory;

import com.example.elancer.enterprise.model.enterpriseintro.MainBusiness;
import com.example.elancer.interviewproject.model.InterviewProject;
import com.example.elancer.waitproject.model.WaitProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WaitProjectRepository extends JpaRepository<WaitProject, Long> {
    List<WaitProject> findByProject_Num(Long projectNum);

    Optional<WaitProject> findByProject_NumAndFreelancer_Num(Long projectNum, Long freelancerNum);

    @Query("select wp from WaitProject wp where wp.project in :numList")
    List<WaitProject> findWaitProject(List<Long> numList);

    long countByProject_Num(Long projectNum);

}
