package com.example.elancer.login.auth.service;

import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.member.domain.Member;
import com.example.elancer.member.repository.FreelancerRepository;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SecurityUserDetailsService implements UserDetailsService {
    private final FreelancerRepository freelancerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = freelancerRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("check id"));

        log.info("---------------------------");
        log.info("freelancer={}", member);

        MemberDetails memberDetails = MemberDetails.userDetailsFrom(member);

        return memberDetails;
    }
}
