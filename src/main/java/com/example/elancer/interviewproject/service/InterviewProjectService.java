package com.example.elancer.interviewproject.service;

import com.example.elancer.common.checker.RightRequestChecker;
import com.example.elancer.freelancer.exception.NotExistFreelancerException;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.repository.FreelancerRepository;
import com.example.elancer.interviewproject.dto.InterviewProjectRequest;
import com.example.elancer.interviewproject.model.InterviewProject;
import com.example.elancer.interviewproject.repository.InterviewProjectRepository;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.project.exception.NotExistProjectException;
import com.example.elancer.project.model.Project;
import com.example.elancer.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InterviewProjectService {

    private final FreelancerRepository freelancerRepository;
    private final InterviewProjectRepository interviewProjectRepository;
    private final ProjectRepository projectRepository;

    @Transactional
    public void createInterviewProject(InterviewProjectRequest interviewProjectRequest, MemberDetails memberDetails) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        Freelancer freelancer = freelancerRepository.findById(interviewProjectRequest.getFreelancerNum()).orElseThrow(NotExistFreelancerException::new);
        Project project = projectRepository.findById(interviewProjectRequest.getProjectNum()).orElseThrow(NotExistProjectException::new);
        interviewProjectRepository.save(InterviewProject.createApplyProject(freelancer, project));
    }
}
