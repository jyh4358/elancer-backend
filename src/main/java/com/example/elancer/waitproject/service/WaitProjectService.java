package com.example.elancer.waitproject.service;

import com.example.elancer.common.checker.RightRequestChecker;
import com.example.elancer.enterprise.repository.EnterpriseRepository;
import com.example.elancer.freelancer.exception.NotExistFreelancerException;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.repository.FreelancerRepository;
import com.example.elancer.interviewproject.exception.NotExistInterviewException;
import com.example.elancer.interviewproject.model.InterviewProject;
import com.example.elancer.interviewproject.repository.InterviewProjectRepository;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.project.exception.NotExistProjectException;
import com.example.elancer.project.model.Project;
import com.example.elancer.project.repository.ProjectRepository;
import com.example.elancer.waitproject.dto.LeaveProjectRequest;
import com.example.elancer.waitproject.dto.WaitProjectRequest;
import com.example.elancer.waitproject.exeption.NotExistWaitProject;
import com.example.elancer.waitproject.model.WaitProject;
import com.example.elancer.waitproject.repsitory.WaitProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WaitProjectService {

    private final FreelancerRepository freelancerRepository;
    private final EnterpriseRepository enterpriseRepository;
    private final WaitProjectRepository waitProjectRepository;
    private final ProjectRepository projectRepository;
    private final InterviewProjectRepository interviewProjectRepository;

    @Transactional
    public void createWaitProject(WaitProjectRequest waitProjectRequest, MemberDetails memberDetails) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        Freelancer freelancer = freelancerRepository.findById(waitProjectRequest.getFreelancerNum()).orElseThrow(NotExistFreelancerException::new);
        Project project = projectRepository.findById(waitProjectRequest.getProjectNum()).orElseThrow(NotExistProjectException::new);
        InterviewProject interviewProject = interviewProjectRepository.findByProject_NumAndFreelancer_Num(project.getNum(), freelancer.getNum()).orElseThrow(NotExistInterviewException::new);
        interviewProjectRepository.delete(interviewProject);
        waitProjectRepository.save(WaitProject.createWaitProject(freelancer, project));

    }

    /**
     * 프리랜서가 프로젝트 투입 취소
     * @param leaveProjectRequest
     * @param memberDetails
     */

    @Transactional
    public void leaveProject(LeaveProjectRequest leaveProjectRequest, MemberDetails memberDetails) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        Freelancer freelancer = freelancerRepository.findById(memberDetails.getId()).orElseThrow(NotExistFreelancerException::new);
        Project project = projectRepository.findById(leaveProjectRequest.getProjectNum()).orElseThrow(NotExistProjectException::new);
        WaitProject waitProject = waitProjectRepository.findByProject_NumAndFreelancer_Num(project.getNum(), freelancer.getNum()).orElseThrow(NotExistWaitProject::new);

        waitProjectRepository.delete(waitProject);
    }

    /**
     * 기업이 프로젝트에 투입 결정된 프리랜서 제외
     * @param withdrawFreelancerRequest
     * @param memberDetails
     */
    @Transactional
    public void excludeProject(WithdrawFreelancerRequest withdrawFreelancerRequest, MemberDetails memberDetails) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        Freelancer freelancer = freelancerRepository.findById(withdrawFreelancerRequest.getFreelancerNum()).orElseThrow(NotExistFreelancerException::new);
        Project project = projectRepository.findById(withdrawFreelancerRequest.getProjectNum()).orElseThrow(NotExistProjectException::new);
        WaitProject waitProject = waitProjectRepository.findByProject_NumAndFreelancer_Num(project.getNum(), freelancer.getNum()).orElseThrow(NotExistWaitProject::new);

        waitProjectRepository.delete(waitProject);
    }
}
