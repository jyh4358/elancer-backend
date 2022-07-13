package com.example.elancer.project.repository;

import com.example.elancer.project.dto.ProjectSearchCondition;
import com.example.elancer.project.model.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.example.elancer.project.model.QProject.project;

@Repository
@RequiredArgsConstructor
public class ProjectSearchRepository {
    private final JPAQueryFactory queryFactory;

    public Slice<Project> findSearchProject(PositionKind positionKind, String skill, ProjectSearchCondition projectSearchCondition, Pageable pageable) {

        BooleanBuilder builder = new BooleanBuilder();
        projectKindEq(positionKind, builder);
        skillContain(skill, builder);

        projectKindEq(projectSearchCondition.getPositionKind(), builder);
        projectSkillEq(projectSearchCondition.getSkills(), builder);
        projectTypeEq(projectSearchCondition.getProjectType(), builder);
        projectDemandCareer(projectSearchCondition.getFreelancerWorkmanShip(), builder);
        projectReginContain(projectSearchCondition.getRegion(), builder);
        projectSearchKeyContain(projectSearchCondition.getSearchKey(), builder);

        List<Project> content = queryFactory
                .selectFrom(project)
                .where(builder)
                .orderBy(project.num.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = isContentSizeGreaterThanPageSize(content, pageable);


        return new SliceImpl<>(hasNext ? subListLastContent(content, pageable) : content, pageable, hasNext);
    }

    private void projectSearchKeyContain(String searchKey, BooleanBuilder builder) {
        if (searchKey == null) {
            return;
        }
        builder.and(project.projectName.containsIgnoreCase(searchKey))
                .or(project.projectName.containsIgnoreCase(searchKey));
    }

    private void projectReginContain(String region, BooleanBuilder builder) {
        if (region == null) {
            return;
        }
        builder.and(project.address.mainAddress.containsIgnoreCase(region));
    }

    private void projectDemandCareer(FreelancerWorkmanShip freelancerWorkmanShip, BooleanBuilder builder) {
        if (freelancerWorkmanShip == null) {
            return;
        }
        if (freelancerWorkmanShip.equals(FreelancerWorkmanShip.JUNIOR)) {
            builder.and(project.careerYear.lt(freelancerWorkmanShip.getCareer()));
        }
        if (freelancerWorkmanShip.equals(FreelancerWorkmanShip.MIDDLE)) {
            builder.and(project.careerYear.lt(freelancerWorkmanShip.getCareer()))
                    .and(project.careerYear.goe(FreelancerWorkmanShip.JUNIOR.getCareer()));
        }
        if (freelancerWorkmanShip.equals(FreelancerWorkmanShip.SENIOR)) {
            builder.and(project.careerYear.goe(freelancerWorkmanShip.getCareer()));
        }
    }

    private void projectTypeEq(ProjectType projectType, BooleanBuilder builder) {
        if (projectType == null) {
            return;
        }

        if (projectType.equals(ProjectType.BOTH_TELEWORKING_WORKING)) {
            builder.and(project.projectType.eq(ProjectType.WORKING))
                    .or(project.projectType.eq(ProjectType.TELEWORKING));
        }
        builder.and(project.projectType.eq(projectType));
    }

    private void projectSkillEq(List<String> skills, BooleanBuilder builder) {
        if (skills == null) {
            return;
        }

        for (String skill : skills) {
            builder.and(project.skill.eq(skill));
        }
    }

    private void projectKindEq(PositionKind positionKind, BooleanBuilder builder) {
        if (positionKind == null) {
            return;
        }
        builder.and(project.positionKind.eq(positionKind));
    }

    private void skillContain(String skill, BooleanBuilder builder) {
        if (skill == null) {
            return;
        }

        builder.and(project.skill.containsIgnoreCase(skill));
    }

    private boolean isContentSizeGreaterThanPageSize(List<Project> content, Pageable pageable) {
        return pageable.isPaged() && content.size() > pageable.getPageSize();
    }

    private static List<Project> subListLastContent(List<Project> content, Pageable pageable) {
        return content.subList(0, pageable.getPageSize());
    }
}
