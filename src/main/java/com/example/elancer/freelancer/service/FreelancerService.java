package com.example.elancer.freelancer.service;

import com.example.elancer.applyproject.model.ApplyProject;
import com.example.elancer.applyproject.repository.ApplyProjectRepository;
import com.example.elancer.common.checker.RightRequestChecker;
import com.example.elancer.freelancer.dto.FreelancerAccountCoverRequest;
import com.example.elancer.freelancer.dto.FreelancerAccountDetailResponse;
import com.example.elancer.freelancer.dto.response.FreelancerObtainOrdersResponse;
import com.example.elancer.freelancer.exception.NotExistFreelancerException;
import com.example.elancer.freelancer.model.CareerForm;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.repository.CareerFormRepository;
import com.example.elancer.freelancer.repository.FreelancerRepository;
import com.example.elancer.interviewproject.model.InterviewProject;
import com.example.elancer.interviewproject.repository.InterviewProjectRepository;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.member.domain.Address;
import com.example.elancer.s3.service.S3UploadService;
import com.example.elancer.waitproject.model.WaitProject;
import com.example.elancer.waitproject.repsitory.WaitProjectSearchRepository;
import com.example.elancer.wishprojects.model.WishProject;
import com.example.elancer.wishprojects.repository.WishProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FreelancerService {
    private final FreelancerRepository freelancerRepository;
    private final CareerFormRepository careerFormRepository;
    private final PasswordEncoder passwordEncoder;
    private final S3UploadService s3UploadService;
    private final ApplyProjectRepository applyProjectRepository;
    private final InterviewProjectRepository interviewProjectRepository;
    private final WaitProjectSearchRepository waitProjectSearchRepository;
    private final WishProjectRepository wishProjectRepository;

    @Transactional
    public void coverFreelancerAccountInfo(MemberDetails memberDetails, FreelancerAccountCoverRequest freelancerAccountCoverRequest) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        Freelancer freelancer = freelancerRepository.findById(memberDetails.getId()).orElseThrow(NotExistFreelancerException::new);
        RightRequestChecker.checkFreelancerAndRequester(freelancer, memberDetails);
        RightRequestChecker.checkPasswordMatch(freelancerAccountCoverRequest.getPassword(), freelancerAccountCoverRequest.getPasswordCheck());
        freelancer.updateFreelancer(
                freelancerAccountCoverRequest.getName(),
                passwordEncoder.encode(freelancerAccountCoverRequest.getPassword()),
                freelancerAccountCoverRequest.getEmail(),
                freelancerAccountCoverRequest.getPhone(),
                freelancerAccountCoverRequest.getWebsite(),
                new Address(
                        freelancerAccountCoverRequest.getCountryType(),
                        freelancerAccountCoverRequest.getZipcode(),
                        freelancerAccountCoverRequest.getMainAddress(),
                        freelancerAccountCoverRequest.getDetailAddress()
                ),
                freelancerAccountCoverRequest.getBirthDate(),
                freelancerAccountCoverRequest.getCareerYear(),
                freelancerAccountCoverRequest.getCareerMonth(),
                freelancerAccountCoverRequest.getHopeMonthMinPay(),
                freelancerAccountCoverRequest.getHopeMonthMaxPay(),
                freelancerAccountCoverRequest.toWorkTypes(),
                freelancerAccountCoverRequest.getWorkEtcField(),
                freelancerAccountCoverRequest.getKosaState(),
                freelancerAccountCoverRequest.getMailReceptionState(),
                freelancerAccountCoverRequest.getPresentWorkState(),
                freelancerAccountCoverRequest.getHopeWorkState(),
                freelancerAccountCoverRequest.getWorkPossibleState(),
                freelancerAccountCoverRequest.getWorkStartPossibleDate(),
                freelancerAccountCoverRequest.getHopeWorkCountry(),
                freelancerAccountCoverRequest.getHopeWorkCity()
        );

        saveCareerForm(freelancerAccountCoverRequest, freelancer);
    }

    private void saveCareerForm(FreelancerAccountCoverRequest freelancerAccountCoverRequest, Freelancer freelancer) {
        if (freelancerAccountCoverRequest.getCareerForm() == null) {
            return;
        }

        CareerForm careerForm = CareerForm.createCareerForm(
                freelancerAccountCoverRequest.getCareerForm().getName(),
                s3UploadService.uploadForMultiFile(freelancerAccountCoverRequest.getCareerForm()),
                freelancer
        );
        careerFormRepository.save(careerForm);
    }

    @Transactional(readOnly = true)
    public FreelancerAccountDetailResponse findDetailFreelancerAccount(MemberDetails memberDetails) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        Freelancer freelancer = freelancerRepository.findById(memberDetails.getId()).orElseThrow(NotExistFreelancerException::new);
        RightRequestChecker.checkFreelancerAndRequester(freelancer, memberDetails);
        return FreelancerAccountDetailResponse.of(freelancer);
    }

    @Transactional(readOnly = true)
    public FreelancerObtainOrdersResponse findFreelancerObtainOrders(MemberDetails memberDetails) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        Freelancer freelancer = freelancerRepository.findById(memberDetails.getId()).orElseThrow(NotExistFreelancerException::new);
        RightRequestChecker.checkFreelancerAndRequester(freelancer, memberDetails);
        List<ApplyProject> applyProjectsByFreelancer = applyProjectRepository.findByFreelancerNum(freelancer.getNum());
        List<InterviewProject> interviewProjectsByFreelancer = interviewProjectRepository.findByFreelancerNum(freelancer.getNum());
        List<WaitProject> waitProjectsByFreelancerAndProjectStatus = waitProjectSearchRepository.findWaitProjectsByFreelancerAndProjectStatus(freelancer.getNum());
        List<WishProject> wishProjectsByFreelancer = wishProjectRepository.findByFreelancerNum(freelancer.getNum());

        return FreelancerObtainOrdersResponse.of(applyProjectsByFreelancer, interviewProjectsByFreelancer, waitProjectsByFreelancerAndProjectStatus, wishProjectsByFreelancer);
    }
}
