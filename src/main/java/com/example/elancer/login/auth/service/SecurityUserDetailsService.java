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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUserId(username).orElseThrow(UserIdNotFoundException::new);

        // 비밀번호가 맞는지는 시큐리티 내부에서 확인해준다.
        MemberDetails memberDetails = MemberDetails.userDetailsFrom(member);

        return memberDetails;
    }
}
