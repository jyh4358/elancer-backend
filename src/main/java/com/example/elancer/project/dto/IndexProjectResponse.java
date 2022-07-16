package com.example.elancer.project.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class IndexProjectResponse {

    private List<ProjectBoxResponse> developerProjectList = new ArrayList<>();
    private List<ProjectBoxResponse> publisherProjectList = new ArrayList<>();
    private List<ProjectBoxResponse> designerProjectList = new ArrayList<>();
    private List<ProjectBoxResponse> plannerProjectList = new ArrayList<>();
    private List<ProjectBoxResponse> etcProjectList = new ArrayList<>();

}
