package com.example.elancer.waitproject.repsitory;

import com.example.elancer.project.model.ProjectStatus;
import com.example.elancer.project.model.QProject;
import com.example.elancer.waitproject.model.WaitProject;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.example.elancer.project.model.QProject.project;
import static com.example.elancer.waitproject.model.QWaitProject.waitProject;

@Repository
@RequiredArgsConstructor
public class WaitProjectSearchRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public List<WaitProject> findWaitProjectsByFreelancerAndProjectStatus(Long freelancerNum) {
        List<WaitProject> results = jpaQueryFactory.selectFrom(waitProject)
                .distinct()
                .innerJoin(waitProject.project, project).fetchJoin()
                .where(waitProject.freelancer.num.eq(freelancerNum)
                        .and(waitProject.project.projectStatus.eq(ProjectStatus.PROGRESS)
                                .or(waitProject.project.projectStatus.eq(ProjectStatus.COMPLETION))))
                .fetch();
        return results;
    }
}
