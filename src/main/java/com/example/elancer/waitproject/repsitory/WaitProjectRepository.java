package com.example.elancer.waitproject.repsitory;

import com.example.elancer.waitproject.model.WaitProject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WaitProjectRepository extends JpaRepository<WaitProject, Long> {
    List<WaitProject> findByProject_Num(Long projectNum);
}
