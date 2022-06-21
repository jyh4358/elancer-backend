package com.example.elancer.project.service;

import com.example.elancer.applyproject.model.ApplyProject;
import com.example.elancer.applyproject.repository.ApplyProjectRepository;
import com.example.elancer.common.checker.RightRequestChecker;
import com.example.elancer.enterprise.exception.NotExistEnterpriseException;
import com.example.elancer.enterprise.model.enterprise.Enterprise;
import com.example.elancer.enterprise.repository.EnterpriseRepository;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.interviewproject.model.InterviewProject;
import com.example.elancer.interviewproject.repository.InterviewProjectRepository;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.project.dto.*;
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
    private final ApplyProjectRepository applyProjectRepository;
    private final InterviewProjectRepository interviewProjectRepository;

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
        project.setEnterprise(enterprise);

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

    public List<DashboardProjectResponse> findDashboardProject(MemberDetails memberDetails) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        List<Project> findProjects = projectRepository.findByEnterprise_Num(memberDetails.getId());
        List<DashboardProjectResponse> findList = findProjects.stream().map(s ->
                DashboardProjectResponse.of(
                        s,
                        (int) applyProjectRepository.countByProject_Num(s.getNum()),
                        (int) interviewProjectRepository.countByProject_Num(s.getNum()),
                        searchApplicantList(s),
                        searchInterviewRequesterList(s)
                )
        ).collect(Collectors.toList());
        return findList;
    }

    public List<ApplicantDto> searchApplicantList(Project project) {
        List<ApplyProject> findApplyProjects = applyProjectRepository.findByProject_Num(project.getNum());
        List<Freelancer> freelancers = findApplyProjects.stream().map(s ->
                s.getFreelancer()
        ).collect(Collectors.toList());

        return ApplicantDto.createApplicantList(freelancers);
    }

    public List<InterviewRequestDto> searchInterviewRequesterList(Project project) {
        List<InterviewProject> findInterviewProjects = interviewProjectRepository.findByProject_Num(project.getNum());


        return findInterviewProjects.stream().map(s ->
                InterviewRequestDto.of(
                        s.getFreelancer(),
                        s.getInterviewStatus()
                )
        ).collect(Collectors.toList());
    }
}
