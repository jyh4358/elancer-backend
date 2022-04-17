package com.example.elancer.freelancer.join.service;

import com.example.elancer.freelancer.join.dto.FreelancerJoinRequest;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.model.FreelancerThumbnail;
import com.example.elancer.freelancer.repository.FreelancerRepository;
import com.example.elancer.member.domain.MemberType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FreelancerJoinService {
    private final FreelancerRepository freelancerRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void joinFreelancer(FreelancerJoinRequest freelancerJoinRequest) {
        freelancerJoinRequest.checkPasswordMatch();
        Freelancer freelancer = Freelancer.createFreelancer(
                freelancerJoinRequest.getMemberId(),
                bCryptPasswordEncoder.encode(freelancerJoinRequest.getMemberPassword()),
                freelancerJoinRequest.getMemberName(),
                freelancerJoinRequest.getMemberPhone(),
                freelancerJoinRequest.getMemberEmail(),
                MemberType.FREELANCER,
                freelancerJoinRequest.getMailReceptionState(),
                freelancerJoinRequest.getWorkPossibleState(),
                freelancerJoinRequest.getWorkStartPossibleDate(),
//                FreelancerThumbnail.createFreelancerThumbnail(String.valueOf(freelancerJoinRequest.getThumbnail()))
                null // 섬네일 저장 로직 따로 빼서 구현하기.
        );

        freelancerRepository.save(freelancer);
    }
}
