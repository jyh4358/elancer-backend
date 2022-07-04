package com.example.elancer.project.repository;

import com.example.elancer.project.model.Project;
import com.example.elancer.project.model.ProjectStatus;
import com.example.elancer.waitproject.model.WaitStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query(value = "select * from Project order by RAND() limit 6", nativeQuery = true)
    List<Project> findRandomProject();

    List<Project> findByEnterprise_Num(Long enterpriseNum);
    List<Project> findByEnterprise_NumAndProjectStatus(Long enterpriseNum, ProjectStatus projectStatus);

    @Query("select p from Project p " +
            "join fetch p.waitProjects wp where p.enterprise.num = :enterprise_num")
    List<Project> findWithWaitProject(@Param("enterprise_num") Long enterprise_num);

    @Query("select distinct p from Project p " +
            "join fetch p.waitProjects wp " +
            "where p.enterprise.num = :enterprise_num and wp.waitStatus = :waitStatus")
    List<Project> findWithWaitProjectAndWaitStatus(@Param("enterprise_num") Long enterprise_num,
                                                   @Param("waitStatus")WaitStatus waitStatus);

    Long countByProjectStatus(ProjectStatus projectStatus);

}
