package com.example.elancer.interviewproject.service;

import com.example.elancer.applyproject.repository.ApplyProjectRepository;
import com.example.elancer.common.checker.RightRequestChecker;
import com.example.elancer.freelancer.exception.NotExistFreelancerException;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.repository.FreelancerRepository;
import com.example.elancer.interviewproject.dto.*;
import com.example.elancer.interviewproject.exception.NotExistInterviewException;
import com.example.elancer.interviewproject.model.InterviewProject;
import com.example.elancer.interviewproject.model.InterviewSatus;
import com.example.elancer.interviewproject.repository.InterviewProjectRepository;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.project.exception.NotExistProjectException;
import com.example.elancer.project.model.Project;
import com.example.elancer.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InterviewProjectService {

    private final FreelancerRepository freelancerRepository;
    private final InterviewProjectRepository interviewProjectRepository;
    private final ProjectRepository projectRepository;
    private final ApplyProjectRepository applyProjectRepository;

    @Transactional
    public void createInterviewProject(CreateInterviewProjectRequest createInterviewProjectRequest, MemberDetails memberDetails) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        // todo - 프로젝트와 프리랜서 check 해줘야하는지 ..
        applyProjectRepository.findById(createInterviewProjectRequest.getApplyProjectNum()).orElseThrow();
//        Project project = projectRepository.findById(createInterviewProjectRequest.getProjectNum()).orElseThrow(NotExistProjectException::new);
//        interviewProjectRepository.save(InterviewProject.createApplyProject(freelancer, project));
    }

    @Transactional
    public void acceptInterview(MemberDetails memberDetails, AcceptInterviewRequest acceptInterviewRequest) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        InterviewProject interviewProject = interviewProjectRepository.findById(acceptInterviewRequest.getInterviewProjectNum()).orElseThrow(NotExistInterviewException::new);
        interviewProject.changeInterviewStatus(InterviewSatus.ACCEPT);
    }

    @Transactional
    public void rejectInterview(MemberDetails memberDetails, RejectInterviewRequest rejectInterviewRequest) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        InterviewProject interviewProject = interviewProjectRepository.findById(rejectInterviewRequest.getInterviewProjectNum()).orElseThrow(NotExistInterviewException::new);
        interviewProject.changeInterviewStatus(InterviewSatus.WAITING);
    }

    public List<InterviewProjectResponse> interviewProjectList(InterviewProjectRequest interviewProjectRequest, MemberDetails memberDetails) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        List<InterviewProject> interviewProjects = interviewProjectRepository.findByProject_Num(interviewProjectRequest.getProjectNum());
        return interviewProjects.stream().map(s ->
                new InterviewProjectResponse(
                        s.getFreelancer().getNum(),
                        s.getFreelancer().getName(),
                        s.getFreelancer().getPhone(),
                        s.getInterviewSatus())).collect(Collectors.toList());
    }
}
