package com.example.elancer.project.repository;

import com.example.elancer.project.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query(value = "select * from Project order by RAND() limit 6", nativeQuery = true)
    List<Project> findRandomProject();
}
