package com.example.elancer.project.dto;

import com.example.elancer.project.model.Project;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IndexProjectResponse {

    private List<ProjectBoxResponse> developerProjectList = new ArrayList<>();
    private List<ProjectBoxResponse> publisherProjectList = new ArrayList<>();
    private List<ProjectBoxResponse> designerProjectList = new ArrayList<>();
    private List<ProjectBoxResponse> plannerProjectList = new ArrayList<>();
    private List<ProjectBoxResponse> etcProjectList = new ArrayList<>();

    public IndexProjectResponse(List<ProjectBoxResponse> developerProjectList, List<ProjectBoxResponse> publisherProjectList, List<ProjectBoxResponse> designerProjectList, List<ProjectBoxResponse> plannerProjectList, List<ProjectBoxResponse> etcProjectList) {
        this.developerProjectList = developerProjectList;
        this.publisherProjectList = publisherProjectList;
        this.designerProjectList = designerProjectList;
        this.plannerProjectList = plannerProjectList;
        this.etcProjectList = etcProjectList;
    }

    public static IndexProjectResponse of(List<Project> developerProjectList, List<Project> publisherProjectList, List<Project> designerProjectList, List<Project> plannerProjectList, List<Project> etcProjectList) {
        return new IndexProjectResponse(
                developerProjectList.stream().map(s ->
                        ProjectBoxResponse.listBoxOf(s)).collect(Collectors.toList()),
                publisherProjectList.stream().map(s ->
                        ProjectBoxResponse.listBoxOf(s)).collect(Collectors.toList()),
                designerProjectList.stream().map(s ->
                        ProjectBoxResponse.listBoxOf(s)).collect(Collectors.toList()),
                plannerProjectList.stream().map(s ->
                        ProjectBoxResponse.listBoxOf(s)).collect(Collectors.toList()),
                etcProjectList.stream().map(s ->
                        ProjectBoxResponse.listBoxOf(s)).collect(Collectors.toList())
        );
    }
}
