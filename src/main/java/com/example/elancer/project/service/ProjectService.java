package com.example.elancer.project.service;

import com.example.elancer.common.checker.RightRequestChecker;
import com.example.elancer.enterprise.model.enterprise.Enterprise;
import com.example.elancer.enterprise.exception.NotExistEnterpriseException;
import com.example.elancer.enterprise.repository.EnterpriseRepository;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.project.dto.ProjectSaveRequest;
import com.example.elancer.project.model.Project;
import com.example.elancer.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final EnterpriseRepository enterpriseRepository;

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
}
