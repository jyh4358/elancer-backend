package com.example.elancer.freelancer.dto.response;

import com.example.elancer.applyproject.model.ApplyProject;
import com.example.elancer.interviewproject.model.InterviewProject;
import com.example.elancer.project.dto.ApplyProjectResponse;
import com.example.elancer.project.dto.InterviewProjectResponse;
import com.example.elancer.project.dto.ProcessingProjectResponse;
import com.example.elancer.project.dto.ProjectBasicResponse;
import com.example.elancer.project.model.Project;
import com.example.elancer.waitproject.model.WaitProject;
import com.example.elancer.wishprojects.model.WishProject;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FreelancerObtainOrdersResponse {
    private int applyProjectCount;
    private int interviewProjectCount;
    private int joinedProjectCount;
    private int wishProjectCount;

    private List<ProjectBasicResponse> applyProjectResponses;
    private List<ProjectBasicResponse> interviewProjectResponses;
    private List<ProjectBasicResponse> joinedProjectResponses;
    private List<ProjectBasicResponse> wishProjectResponses;

    public FreelancerObtainOrdersResponse(
            int applyProjectCount,
            int interviewProjectCount,
            int joinedProjectCount,
            int wishProjectCount,
            List<ProjectBasicResponse> applyProjectResponses,
            List<ProjectBasicResponse> interviewProjectResponses,
            List<ProjectBasicResponse> projectResponses,
            List<ProjectBasicResponse> wishProjectResponses
    ) {
        this.applyProjectCount = applyProjectCount;
        this.interviewProjectCount = interviewProjectCount;
        this.joinedProjectCount = joinedProjectCount;
        this.wishProjectCount = wishProjectCount;
        this.applyProjectResponses = applyProjectResponses;
        this.interviewProjectResponses = interviewProjectResponses;
        this.joinedProjectResponses = projectResponses;
        this.wishProjectResponses = wishProjectResponses;
    }

    public static FreelancerObtainOrdersResponse of(
            List<ApplyProject> applyProjects,
            List<InterviewProject> interviewProjects,
            List<WaitProject> joinedProjects,
            List<WishProject> wishProjects
    ) {
        return new FreelancerObtainOrdersResponse(
                applyProjects.size(),
                interviewProjects.size(),
                joinedProjects.size(),
                wishProjects.size(),
                ProjectBasicResponse.listOf(applyProjects.stream().map(ApplyProject::getProject).collect(Collectors.toList())),
                ProjectBasicResponse.listOf(interviewProjects.stream().map(InterviewProject::getProject).collect(Collectors.toList())),
                ProjectBasicResponse.listOf(joinedProjects.stream().map(WaitProject::getProject).collect(Collectors.toList())),
                ProjectBasicResponse.listOf(wishProjects.stream().map(WishProject::getProject).collect(Collectors.toList()))
        );
    }
}
