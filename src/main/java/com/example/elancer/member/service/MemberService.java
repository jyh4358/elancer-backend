package com.example.elancer.member.service;

import com.example.elancer.common.checker.RightRequestChecker;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.member.domain.Member;
import com.example.elancer.member.dto.MemberContactResponse;
import com.example.elancer.member.exception.NotExistMemberException;
import com.example.elancer.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberContactResponse findMemberContact(MemberDetails memberDetails) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        Member member = memberRepository.findById(memberDetails.getId()).orElseThrow(NotExistMemberException::new);

        return MemberContactResponse.of(member);
    }
}
