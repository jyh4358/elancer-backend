package com.example.elancer.common.basetest;

import com.example.elancer.common.database.DatabaseCleaner;
import com.example.elancer.enterprise.repository.EnterpriseRepository;
import com.example.elancer.freelancer.repository.FreelancerRepository;
import com.example.elancer.freelancerprofile.repository.FreelancerProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("h2")
@SpringBootTest
public class ServiceBaseTest {
    @Autowired
    protected FreelancerRepository freelancerRepository;
    @Autowired
    protected FreelancerProfileRepository freelancerProfileRepository;
    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected DatabaseCleaner databaseCleaner;
}
