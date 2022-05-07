package com.example.elancer.login.auth.service;

import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.login.auth.exception.UserIdNotFoundException;
import com.example.elancer.member.domain.Member;
import com.example.elancer.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SecurityUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Member member = memberRepository.findByUserId(userId).orElseThrow(UserIdNotFoundException::new);

        MemberDetails memberDetails = MemberDetails.userDetailsFrom(member);

        log.info("UserDeailService 호출========================");

        return memberDetails;
    }
}
