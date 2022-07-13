package com.example.elancer.project.dto;

import com.example.elancer.project.model.FreelancerWorkmanShip;
import com.example.elancer.project.model.PositionKind;
import com.example.elancer.project.model.ProjectType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class ProjectSearchCondition {
    private PositionKind positionKind;
    private List<String> skills;
    private ProjectType projectType;
    private FreelancerWorkmanShip freelancerWorkmanShip;
    private String region;
    private String searchKey;
}
