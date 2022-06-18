package com.example.elancer.project.dto;

import com.example.elancer.project.model.ProjectType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RecommendProjectResponse {

    private ProjectType projectType;

    private Long endDays;

    private String skill;

    private String projectName;




}
