package com.example.elancer.domains.auth.service;

import com.example.elancer.domains.auth.dto.MemberDetails;
import com.example.elancer.domains.user.entity.Freelancer;
import com.example.elancer.domains.user.repository.FreelancerRepository;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@ToString
@Service
public class SecurityUserDetailsService implements UserDetailsService {

    private final FreelancerRepository freelancerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("SecurityUserDetailsService username={}", username);

//        Optional<Freelancer> result = freelancerRepository.findById(username);
//
//        if (result.isEmpty()) {
//            throw new UsernameNotFoundException("check id");
//        }
//        Freelancer freelancer = result.get();


        Freelancer freelancer = freelancerRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("check id"));


        log.info("---------------------------");
        log.info("freelancer={}", freelancer);


        MemberDetails memberDetails = MemberDetails.userDetailsFrom(freelancer);


        return memberDetails;
    }
}
