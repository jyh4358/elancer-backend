package com.example.elancer.waitproject.repsitory;

import com.example.elancer.enterprise.model.enterpriseintro.MainBusiness;
import com.example.elancer.interviewproject.model.InterviewProject;
import com.example.elancer.waitproject.model.WaitProject;
import com.example.elancer.waitproject.model.WaitStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WaitProjectRepository extends JpaRepository<WaitProject, Long> {
    List<WaitProject> findByProject_Num(Long projectNum);

    List<WaitProject> findByProject_NumAndWaitStatus(Long projectNum, WaitStatus waitStatus);

    Optional<WaitProject> findByProject_NumAndFreelancer_Num(Long projectNum, Long freelancerNum);

    @Query("select wp from WaitProject wp where wp.project in :numList")
    List<WaitProject> findWaitProject(List<Long> numList);

    long countByProject_Num(Long projectNum);
    long countByProject_NumAndWaitStatus(Long projectNum, WaitStatus waitStatus);

    @Query("select count(wp.project.num) from WaitProject wp where wp.project.num in :numList and wp.waitStatus = 'WAITING' group by wp.project.num")
    List<Long> countWaitProject(@Param("numList") List<Long> numList);


    void deleteAllByProject_Num(Long projectNum);
}
