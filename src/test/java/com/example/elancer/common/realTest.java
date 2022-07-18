package com.example.elancer.common;

import com.example.elancer.freelancer.repository.FreelancerRepository;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.freelancerprofile.model.position.developer.Developer;
import com.example.elancer.freelancerprofile.repository.FreelancerProfileRepository;
import com.example.elancer.freelancerprofile.repository.position.designer.DesignerRepository;
import com.example.elancer.freelancerprofile.repository.position.developer.DeveloperRepository;
import com.example.elancer.freelancerprofile.repository.position.etc.PositionEtcRepository;
import com.example.elancer.freelancerprofile.repository.position.planner.PlannerRepository;
import com.example.elancer.freelancerprofile.repository.position.publisher.PublisherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class realTest {

    @Autowired
    private FreelancerRepository freelancerRepository;

    @Autowired
    private FreelancerProfileRepository freelancerProfileRepository;

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private DesignerRepository designerRepository;

    @Autowired
    private PlannerRepository plannerRepository;

    @Autowired
    private PositionEtcRepository positionEtcRepository;

    @DisplayName("")
    @Test
    public void name() {
        //given
//
//        Developer.createBasicDeveloper(PositionType.DEVELOPER, )
//        developerRepository.save()

        //when
        //then
    }
}
