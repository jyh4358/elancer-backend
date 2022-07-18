package com.example.elancer.freelancer.join.service;

import com.example.elancer.common.checker.RightRequestChecker;
import com.example.elancer.freelancer.join.dto.FreelancerJoinRequest;
import com.example.elancer.freelancer.join.exception.ExistUserIdException;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.model.FreelancerThumbnail;
import com.example.elancer.freelancer.repository.FreelancerRepository;
import com.example.elancer.freelancer.repository.FreelancerThumbnailRepository;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.freelancerprofile.repository.FreelancerProfileRepository;
import com.example.elancer.member.domain.MemberType;
import com.example.elancer.s3.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FreelancerJoinService {

    private final FreelancerRepository freelancerRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final FreelancerProfileRepository freelancerProfileRepository;
    private final FreelancerThumbnailRepository freelancerThumbnailRepository;

    @Transactional
    public void joinFreelancer(FreelancerJoinRequest freelancerJoinRequest) {
        RightRequestChecker.checkPasswordMatch(freelancerJoinRequest.getMemberPassword(), freelancerJoinRequest.getMemberPasswordCheck());
        checkExistUserId(freelancerJoinRequest);
        Freelancer freelancer = Freelancer.createFreelancer(
                freelancerJoinRequest.getMemberId(),
                bCryptPasswordEncoder.encode(freelancerJoinRequest.getMemberPassword()),
                freelancerJoinRequest.getMemberName(),
                freelancerJoinRequest.getMemberPhone(),
                freelancerJoinRequest.getMemberEmail(),
                null,
                null,
                MemberType.FREELANCER,
                freelancerJoinRequest.getMailReceptionState(),
                freelancerJoinRequest.getWorkPossibleState(),
                freelancerJoinRequest.getWorkStartPossibleDate()
        );

        Freelancer savedFreelancer = freelancerRepository.save(freelancer);

        initializeFreelancerProfile(savedFreelancer, freelancerJoinRequest.getPositionType());
        saveFreelancerThumbnail(freelancerJoinRequest, savedFreelancer);
    }

    private void saveFreelancerThumbnail(FreelancerJoinRequest freelancerJoinRequest, Freelancer savedFreelancer) {
        if (freelancerJoinRequest.getThumbnail() == null) {
            return;
        }

        freelancerThumbnailRepository.save(FreelancerThumbnail.createFreelancerThumbnail(freelancerJoinRequest.getThumbnail(), savedFreelancer));
    }

    private void initializeFreelancerProfile(Freelancer savedFreelancer, PositionType positionType) {
        freelancerProfileRepository.save(new FreelancerProfile(null, savedFreelancer, positionType));
    }

    private void checkExistUserId(FreelancerJoinRequest freelancerJoinRequest) {
        if (freelancerRepository.existsByUserId(freelancerJoinRequest.getMemberId())) {
            throw new ExistUserIdException();
        }
    }
}
