package com.example.elancer.integrate.common;

import com.example.elancer.common.database.DatabaseCleaner;
import com.example.elancer.freelancer.repository.FreelancerRepository;
import com.example.elancer.freelancerprofile.repository.FreelancerProfileRepository;
import com.example.elancer.token.service.JwtTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("h2")
@AutoConfigureMockMvc
public class IntegrateBaseTest {
    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected FreelancerRepository freelancerRepository;

    @Autowired
    protected FreelancerProfileRepository freelancerProfileRepository;

    @Autowired
    protected DatabaseCleaner databaseCleaner;

    @Autowired
    protected JwtTokenService jwtTokenService;

    @Autowired
    protected PasswordEncoder passwordEncoder;

}
