package com.example.elancer.wishprojects.service;

import com.example.elancer.common.checker.RightRequestChecker;
import com.example.elancer.freelancer.exception.NotExistFreelancerException;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.repository.FreelancerRepository;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.project.model.Project;
import com.example.elancer.project.repository.ProjectRepository;
import com.example.elancer.wishprojects.dto.WishProjectSaveRequest;
import com.example.elancer.wishprojects.exception.NotExistProjectException;
import com.example.elancer.wishprojects.exception.NotExistWishProjectException;
import com.example.elancer.wishprojects.model.WishProject;
import com.example.elancer.wishprojects.repository.WishProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WishProjectService {
    private final WishProjectRepository wishProjectRepository;
    private final ProjectRepository projectRepository;
    private final FreelancerRepository freelancerRepository;

    @Transactional
    public void saveWishProject(MemberDetails memberDetails, WishProjectSaveRequest wishProjectSaveRequest) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        Freelancer freelancer = freelancerRepository.findById(memberDetails.getId()).orElseThrow(NotExistFreelancerException::new);
        Project project = projectRepository.findById(wishProjectSaveRequest.getProjectNum()).orElseThrow(NotExistProjectException::new);
        wishProjectRepository.save(WishProject.createWishProject(freelancer, project));
    }

    @Transactional
    public void deleteWishProject(MemberDetails memberDetails, Long wishProjectNum) {
        WishProject wishProject = wishProjectRepository.findById(wishProjectNum).orElseThrow(NotExistWishProjectException::new);
        RightRequestChecker.checkFreelancerAndRequester(wishProject.getFreelancer(), memberDetails);
        wishProjectRepository.deleteById(wishProject.getNum());
    }
}
