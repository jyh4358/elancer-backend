package com.example.elancer.project.service;

import com.example.elancer.common.checker.RightRequestChecker;
import com.example.elancer.enterprise.exception.NotExistEnterpriseException;
import com.example.elancer.enterprise.model.enterprise.Enterprise;
import com.example.elancer.enterprise.repository.EnterpriseRepository;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.project.dto.ProjectDeleteRequest;
import com.example.elancer.project.dto.ProjectProcessingRequest;
import com.example.elancer.project.dto.ProjectSaveRequest;
import com.example.elancer.project.dto.RecommendProjectResponse;
import com.example.elancer.project.exception.NotEnoughHeadCount;
import com.example.elancer.project.model.Project;
import com.example.elancer.project.model.ProjectStatus;
import com.example.elancer.project.repository.ProjectRepository;
import com.example.elancer.waitproject.model.WaitProject;
import com.example.elancer.waitproject.repsitory.WaitProjectRepository;
import com.example.elancer.wishprojects.exception.NotExistProjectException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final EnterpriseRepository enterpriseRepository;
    private final WaitProjectRepository waitProjectRepository;

    @Transactional
    public void saveProject(MemberDetails memberDetails, ProjectSaveRequest projectSaveRequest) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        Enterprise enterprise = enterpriseRepository.findById(memberDetails.getId()).orElseThrow(NotExistEnterpriseException::new);

        enterprise.updateEnterpriseInfo(
                projectSaveRequest.getCompanyName(),
                projectSaveRequest.getName(),
                projectSaveRequest.getPosition(),
                projectSaveRequest.getPhone(),
                projectSaveRequest.getTelNumber(),
                projectSaveRequest.getEmail());

        Project project = projectSaveRequest.toEntity();

        projectRepository.save(project);
    }

    @Transactional
    public void deleteProject(MemberDetails memberDetails, ProjectDeleteRequest projectDeleteRequest) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        Project project = projectRepository.findById(projectDeleteRequest.getProjectNum()).orElseThrow(NotExistProjectException::new);

        projectRepository.delete(project);
    }

    @Transactional
    public void startProject(MemberDetails memberDetails, ProjectProcessingRequest projectProcessingRequest) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        Project project = projectRepository.findById(projectProcessingRequest.getProjectNum()).orElseThrow(NotExistProjectException::new);
        RightRequestChecker.checkMemberAndProject(memberDetails, project);
        List<WaitProject> waitProjects = waitProjectRepository.findByProject_Num(project.getNum());

        if (waitProjects.size() > project.getHeadCount()) {
            project.changeProjectStatus(ProjectStatus.PROGRESS);
        } else {
            throw new NotEnoughHeadCount();
        }
    }

    @Transactional
    public void finishProject(MemberDetails memberDetails, ProjectProcessingRequest projectProcessingRequest) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        Project project = projectRepository.findById(projectProcessingRequest.getProjectNum()).orElseThrow(NotExistProjectException::new);
        RightRequestChecker.checkMemberAndProject(memberDetails, project);

        project.changeProjectStatus(ProjectStatus.COMPLETION);
    }


    public List<RecommendProjectResponse> findRecommendProject() {
        List<Project> recommendProjects = projectRepository.findRandomProject();
        System.out.println("recommendProjects.size() = " + recommendProjects.size());
        return recommendProjects.stream().map(s ->
                RecommendProjectResponse.of(s)).collect(Collectors.toList());
    }
}
