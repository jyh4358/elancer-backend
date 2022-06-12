package com.example.elancer.project.service;

import com.example.elancer.common.checker.RightRequestChecker;
import com.example.elancer.enterprise.model.enterprise.Enterprise;
import com.example.elancer.enterprise.exception.NotExistEnterpriseException;
import com.example.elancer.enterprise.repository.EnterpriseRepository;
import com.example.elancer.interviewproject.model.InterviewProject;
import com.example.elancer.interviewproject.repository.InterviewProjectRepository;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.project.dto.ProjectDeleteRequest;
import com.example.elancer.project.dto.ProjectSaveRequest;
import com.example.elancer.project.dto.ProjectStartRequest;
import com.example.elancer.project.model.Project;
import com.example.elancer.project.repository.ProjectRepository;
import com.example.elancer.wishprojects.exception.NotExistProjectException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final EnterpriseRepository enterpriseRepository;
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

        projectRepository.save(project);
    }

    @Transactional
    public void deleteProject(MemberDetails memberDetails, ProjectDeleteRequest projectDeleteRequest) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        Project project = projectRepository.findById(projectDeleteRequest.getProjectNum()).orElseThrow(NotExistProjectException::new);

        projectRepository.delete(project);
    }

    @Transactional
    public void startProject(MemberDetails memberDetails, ProjectStartRequest projectStartRequest) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        Project project = projectRepository.findById(projectStartRequest.getProjectNum()).orElseThrow(NotExistProjectException::new);
        List<InterviewProject> interviews = interviewProjectRepository.findByProject_Num(projectStartRequest.getProjectNum());

        // todo - 06/12 미팅 이후에 구현

    }

}
