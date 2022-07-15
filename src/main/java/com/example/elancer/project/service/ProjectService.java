package com.example.elancer.project.service;

import com.example.elancer.applyproject.model.ApplyProject;
import com.example.elancer.applyproject.repository.ApplyProjectRepository;
import com.example.elancer.common.checker.RightRequestChecker;
import com.example.elancer.enterprise.exception.NotExistEnterpriseException;
import com.example.elancer.enterprise.model.enterprise.Enterprise;
import com.example.elancer.enterprise.repository.EnterpriseRepository;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancerprofile.model.position.Position;
import com.example.elancer.interviewproject.model.InterviewProject;
import com.example.elancer.interviewproject.repository.InterviewProjectRepository;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.project.dto.*;
import com.example.elancer.project.model.*;
import com.example.elancer.project.repository.ProjectRepository;
import com.example.elancer.project.repository.ProjectSearchRepository;
import com.example.elancer.waitproject.model.WaitProject;
import com.example.elancer.waitproject.model.WaitStatus;
import com.example.elancer.waitproject.repsitory.WaitProjectRepository;
import com.example.elancer.wishprojects.exception.NotExistProjectException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectSearchRepository projectSearchRepository;
    private final EnterpriseRepository enterpriseRepository;
    private final WaitProjectRepository waitProjectRepository;
    private final ApplyProjectRepository applyProjectRepository;
    private final InterviewProjectRepository interviewProjectRepository;

    public ProjectDetailResponse findDetailProject(Long projectNum) {
        Project project = projectRepository.findById(projectNum).orElseThrow(NotExistProjectException::new);
        List<ApplyProject> applyFreelancer = applyProjectRepository.findByProject_Num(project.getNum());

        List<SimpleFreelancerDto> simpleFreelancerDtoList = applyFreelancer.stream().map(s ->
                SimpleFreelancerDto.of(s.getFreelancer())
        ).collect(Collectors.toList());

        return ProjectDetailResponse.of(project, simpleFreelancerDtoList);
    }


    public Slice<ProjectBoxResponse> searchProjectList(String position, String skill, PositionKind positionKind, List<String> skills, ProjectType projectType, FreelancerWorkmanShip freelancerWorkmanShip, String region, String searchKey, Pageable pageable) {

        Slice<Project> searchProject = projectSearchRepository.findSearchProject(
                getPositionKind(position),
                skill,
                positionKind,
                skills,
                projectType,
                freelancerWorkmanShip,
                region,
                searchKey,
                pageable);
        return searchProject.map(s ->
                ProjectBoxResponse.listBoxOf(s));

    }

    public IndexProjectResponse findIndexProjectList() {
        List<Project> developProject = projectRepository.findTop3ByPositionKindOrderByNumDesc(PositionKind.DEVELOPER);
        List<Project> publisherProject= projectRepository.findTop3ByPositionKindOrderByNumDesc(PositionKind.PUBLISHER);
        List<Project> designerProject= projectRepository.findTop3ByPositionKindOrderByNumDesc(PositionKind.DESIGNER);
        List<Project> plannerProject= projectRepository.findTop3ByPositionKindOrderByNumDesc(PositionKind.PLANNER);
        List<Project> etcProject= projectRepository.findTop3ByPositionKindOrderByNumDesc(PositionKind.ETC);

        IndexProjectResponse indexProjectResponse = new IndexProjectResponse();

        indexProjectResponse.setDeveloperProjectList(developProject.stream().map(s ->
                ProjectBoxResponse.listBoxOf(s)
        ).collect(Collectors.toList()));

        indexProjectResponse.setPublisherProjectList(publisherProject.stream().map(s ->
                ProjectBoxResponse.listBoxOf(s)
        ).collect(Collectors.toList()));

        indexProjectResponse.setDesignerProjectList(designerProject.stream().map(s ->
                ProjectBoxResponse.listBoxOf(s)
        ).collect(Collectors.toList()));

        indexProjectResponse.setPlannerProjectList(plannerProject.stream().map(s ->
                ProjectBoxResponse.listBoxOf(s)
        ).collect(Collectors.toList()));

        indexProjectResponse.setEtcProjectList(etcProject.stream().map(s ->
                ProjectBoxResponse.listBoxOf(s)
        ).collect(Collectors.toList()));

        return indexProjectResponse;
    }

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

        List<WaitProject> findWaitProject = waitProjectRepository.findByProject_Num(project.getNum());
        findWaitProject.forEach(
                s -> s.changeWaitStatus()
        );
        applyProjectRepository.deleteAllByProject_Num(project.getNum());
        interviewProjectRepository.deleteAllByProject_Num(project.getNum());

        project.changeProjectStatus(ProjectStatus.PROGRESS);
    }

    @Transactional
    public void finishProject(MemberDetails memberDetails, ProjectProcessingRequest projectProcessingRequest) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        Project project = projectRepository.findById(projectProcessingRequest.getProjectNum()).orElseThrow(NotExistProjectException::new);
        RightRequestChecker.checkMemberAndProject(memberDetails, project);

        project.changeProjectStatus(ProjectStatus.COMPLETION);
    }


    public List<ProjectBoxResponse> findRecommendProject() {
        List<Project> recommendProjects = projectRepository.findRandomProject();
        System.out.println("recommendProjects.size() = " + recommendProjects.size());
        return recommendProjects.stream().map(s ->
                ProjectBoxResponse.cardBoxOf(s)).collect(Collectors.toList());
    }

    public ProjectListCount projectCount(MemberDetails memberDetails) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        List<Project> findProjects = projectRepository.findByEnterprise_Num(memberDetails.getId());
        List<Long> findProjectNumList = findProjects.stream().map(s -> s.getNum()).collect(Collectors.toList());

        ProjectListCount projectListCount = ProjectListCount.of(
                applyProjectRepository.countByProject_NumGroupByProject_Num(findProjectNumList).size(),
                interviewProjectRepository.countInterviewProject(findProjectNumList).size(),
                waitProjectRepository.countWaitProject(findProjectNumList).size(),
                projectRepository.countByProjectStatus(ProjectStatus.PROGRESS).intValue(),
                projectRepository.countByProjectStatus(ProjectStatus.COMPLETION).intValue()
        );

        return projectListCount;
    }

    public List<DashboardProjectResponse> findDashboardProject(MemberDetails memberDetails) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        List<Project> findProjects = projectRepository.findByEnterprise_Num(memberDetails.getId());
        List<DashboardProjectResponse> findList = findProjects.stream().map(s ->
                DashboardProjectResponse.of(
                        s,
                        (int) applyProjectRepository.countByProject_Num(s.getNum()),
                        (int) interviewProjectRepository.countByProject_Num(s.getNum()),
                        (int) waitProjectRepository.countByProject_NumAndWaitStatus(s.getNum(), WaitStatus.WAITING),
                        (int) waitProjectRepository.countByProject_NumAndWaitStatus(s.getNum(), WaitStatus.WORKING),
                        searchApplicantList(s),
                        searchInterviewRequesterList(s),
                        searchWaitFreelancerList(s, WaitStatus.WAITING),
                        searchWaitFreelancerList(s, WaitStatus.WORKING)
                )
        ).collect(Collectors.toList());
        return findList;
    }
    public List<ApplyProjectResponse> findApplyProject(MemberDetails memberDetails) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        List<Project> findProjects = projectRepository.findByEnterprise_Num(memberDetails.getId());
        List<Project> existApplyProject = findProjects.stream().filter(s -> s.getApplyProjects().size() > 0).collect(Collectors.toList());
        List<ApplyProjectResponse> findList = existApplyProject.stream().map(s ->
                ApplyProjectResponse.of(
                        s,
                        (int) applyProjectRepository.countByProject_Num(s.getNum()),
                        (int) interviewProjectRepository.countByProject_Num(s.getNum()),
                        searchApplicantList(s),
                        searchInterviewRequesterList(s)
                )
        ).collect(Collectors.toList());

        return findList;
    }

    public List<InterviewProjectResponse> findInterviewProject(MemberDetails memberDetails) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        List<Project> findProjects = projectRepository.findByEnterprise_Num(memberDetails.getId());
        List<Project> existInterviewProject = findProjects.stream().filter(s -> s.getInterviewProjects().size() > 0).collect(Collectors.toList());
        List<InterviewProjectResponse> findList = existInterviewProject.stream().map(s ->
                InterviewProjectResponse.of(
                        s,
                        (int) applyProjectRepository.countByProject_Num(s.getNum()),
                        (int) interviewProjectRepository.countByProject_Num(s.getNum()),
                        searchApplicantList(s),
                        searchInterviewRequesterList(s)
                )
        ).collect(Collectors.toList());
        return findList;
    }

    public List<WaitProjectResponse> findWaitProject(MemberDetails memberDetails) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        List<Project> findProjects = projectRepository.findWithWaitProjectAndWaitStatus(memberDetails.getId(), WaitStatus.WAITING);
        List<WaitProjectResponse> findList = findProjects.stream().map(s ->
                WaitProjectResponse.of(
                        s,
                        (int) waitProjectRepository.countByProject_NumAndWaitStatus(s.getNum(), WaitStatus.WAITING),
                        searchWaitFreelancerList(s, WaitStatus.WAITING)
                )
        ).collect(Collectors.toList());

        return findList;
    }

    public List<ProcessingProjectResponse> findProcessingProject(MemberDetails memberDetails) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        List<Project> findProjects = projectRepository.findByEnterprise_NumAndProjectStatus(memberDetails.getId(), ProjectStatus.PROGRESS);
        List<ProcessingProjectResponse> findList = findProjects.stream().map(s ->
                ProcessingProjectResponse.of(
                        s,
                        (int) waitProjectRepository.countByProject_NumAndWaitStatus(s.getNum(), WaitStatus.WORKING),
                        searchWaitFreelancerList(s, WaitStatus.WORKING)
                )).collect(Collectors.toList());
        return findList;
    }

    public List<ProcessingProjectResponse> findFinishProject(MemberDetails memberDetails) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        List<Project> findProjects = projectRepository.findByEnterprise_NumAndProjectStatus(memberDetails.getId(), ProjectStatus.COMPLETION);
        List<ProcessingProjectResponse> findList = findProjects.stream().map(s ->
                ProcessingProjectResponse.of(
                        s,
                        (int) waitProjectRepository.countByProject_NumAndWaitStatus(s.getNum(), WaitStatus.WORKING),
                        searchWaitFreelancerList(s, WaitStatus.WORKING)
                )).collect(Collectors.toList());
        return findList;
    }


    public List<ApplicantDto> searchApplicantList(Project project) {
        List<ApplyProject> findApplyProjects = applyProjectRepository.findByProject_Num(project.getNum());
        List<Freelancer> freelancers = findApplyProjects.stream().map(s ->
                s.getFreelancer()
        ).collect(Collectors.toList());

        return ApplicantDto.createApplicantList(freelancers);
    }

    public List<InterviewFreelancerDto> searchInterviewRequesterList(Project project) {
        List<InterviewProject> findInterviewProjects = interviewProjectRepository.findByProject_Num(project.getNum());


        return findInterviewProjects.stream().map(s ->
                InterviewFreelancerDto.of(
                        s.getFreelancer(),
                        s.getInterviewStatus()
                )
        ).collect(Collectors.toList());
    }
    private List<WaitFreelancerDto> searchWaitFreelancerList(Project project, WaitStatus waitStatus) {
        List<WaitProject> findWaitFreelancer = waitProjectRepository.findByProject_NumAndWaitStatus(project.getNum(), waitStatus);

        return findWaitFreelancer.stream().map(s ->
                WaitFreelancerDto.of(
                        s.getFreelancer()
                )
        ).collect(Collectors.toList());
    }


    private PositionKind getPositionKind(String position) {
        if (position == null) {
            return null;
        }
        PositionKind positionKind;
        switch (position) {
            case "DEVELOPER":
                positionKind = PositionKind.DEVELOPER;
                break;
            case "PUBLISHER":
                positionKind = PositionKind.PUBLISHER;
                break;
            case "DESIGNER":
                positionKind = PositionKind.DESIGNER;
                break;
            case "PLANNER":
                positionKind = PositionKind.PLANNER;
                break;
            case "ETC":
                positionKind = PositionKind.ETC;
                break;
            default:
                positionKind = null;
        }

        return positionKind;
    }


}
