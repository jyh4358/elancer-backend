package com.example.elancer.integrate.freelancerprofile;

import com.example.elancer.common.FreelancerHelper;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.model.IntroBackGround;
import com.example.elancer.freelancer.model.MailReceptionState;
import com.example.elancer.freelancer.model.WorkPossibleState;
import com.example.elancer.freelancer.repository.FreelancerRepository;
import com.example.elancer.freelancerprofile.controller.FreelancerProfileControllerPath;
import com.example.elancer.freelancerprofile.dto.AcademicAbilityCoverRequest;
import com.example.elancer.freelancerprofile.dto.AcademicAbilityCoverRequests;
import com.example.elancer.freelancerprofile.dto.CareerCoverRequest;
import com.example.elancer.freelancerprofile.dto.CareerCoverRequests;
import com.example.elancer.freelancerprofile.dto.IntroduceCoverRequest;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.academic.AcademicAbility;
import com.example.elancer.freelancerprofile.model.academic.state.AcademicState;
import com.example.elancer.freelancerprofile.model.academic.state.SchoolLevel;
import com.example.elancer.freelancerprofile.model.career.Career;
import com.example.elancer.freelancerprofile.model.career.CompanyPosition;
import com.example.elancer.freelancerprofile.repository.FreelancerProfileRepository;
import com.example.elancer.freelancerprofile.repository.academic.AcademicRepository;
import com.example.elancer.freelancerprofile.repository.career.CareerRepository;
import com.example.elancer.integrate.common.IntegrateBaseTest;
import com.example.elancer.member.domain.MemberType;
import io.restassured.RestAssured;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import java.awt.*;
import java.time.LocalDate;
import java.util.Arrays;

public class FreelancerProfileIntegrateTest extends IntegrateBaseTest {

    @Autowired
    private FreelancerRepository freelancerRepository;

    @Autowired
    private FreelancerProfileRepository freelancerProfileRepository;

    @Autowired
    private AcademicRepository academicRepository;

    @Autowired
    private CareerRepository careerRepository;

    @DisplayName("프리랜서 프로필 소개정보 저장 테스트")
    @Test
    public void 프리랜서_프로필_소개정보_저장() {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository));
        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer));

        IntroduceCoverRequest introduceCoverRequest = new IntroduceCoverRequest("introName", IntroBackGround.COBALT_BLUE, "introVideoUrl", "introContent");

        String path = FreelancerProfileControllerPath.FREELANCER_PROFILE_INTRO_COVER.replace("{profileNum}", String.valueOf(freelancerProfile.getNum()));

        //when
        RestAssured
                .given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(introduceCoverRequest)
                    .log().all()
                .when()
                    .post(path)
                .then()
                    .statusCode(HttpStatus.OK.value())
        ;

        //then
        FreelancerProfile updatedFreelancerProfile = freelancerProfileRepository.findById(freelancerProfile.getNum()).get();
        Assertions.assertThat(updatedFreelancerProfile.getIntroduceName()).isEqualTo(introduceCoverRequest.getIntroName());
        Assertions.assertThat(updatedFreelancerProfile.getIntroBackGround()).isEqualTo(introduceCoverRequest.getIntroBackGround());
        Assertions.assertThat(updatedFreelancerProfile.getIntroduceContent()).isEqualTo(introduceCoverRequest.getIntroContent());
        Assertions.assertThat(updatedFreelancerProfile.getIntroduceVideoURL()).isEqualTo(introduceCoverRequest.getIntroVideoUrl());
    }

    @DisplayName("프리랜서 프로필 학력 저장 테스트")
    @Test
    public void 프리랜서_프로필_학력정보_저장() {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository));
        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer));

        AcademicAbilityCoverRequest academicAbilityCoverRequest = new AcademicAbilityCoverRequest(
                "schoolName",
                SchoolLevel.HIGH_SCHOOL,
                LocalDate.of(2015, 02, 01),
                LocalDate.of(2018, 02, 01),
                AcademicState.GRADUATION,
                "문과"
        );

        String path = FreelancerProfileControllerPath.FREELANCER_PROFILE_ACADEMIC_COVER.replace("{profileNum}", String.valueOf(freelancerProfile.getNum()));

        //when
        RestAssured
                .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new AcademicAbilityCoverRequests(Arrays.asList(academicAbilityCoverRequest)))
                .log().all()
                .when()
                .post(path)
                .then()
                .statusCode(HttpStatus.OK.value())
        ;

        //then
        AcademicAbility academicAbility = academicRepository.findAll().get(0);
        Assertions.assertThat(academicAbility.getSchoolName()).isEqualTo(academicAbilityCoverRequest.getSchoolName());
        Assertions.assertThat(academicAbility.getSchoolLevel()).isEqualTo(academicAbilityCoverRequest.getSchoolLevel());
        Assertions.assertThat(academicAbility.getEnterSchoolDate()).isEqualTo(academicAbilityCoverRequest.getEnterSchoolDate());
        Assertions.assertThat(academicAbility.getGraduationDate()).isEqualTo(academicAbilityCoverRequest.getGraduationDate());
        Assertions.assertThat(academicAbility.getAcademicState()).isEqualTo(academicAbilityCoverRequest.getAcademicState());
        Assertions.assertThat(academicAbility.getMajorName()).isEqualTo(academicAbilityCoverRequest.getMajorName());
    }

    @DisplayName("프리랜서 프로필 경력 저장 테스트")
    @Test
    public void 프리랜서_프로필_경력정보_저장() {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository));
        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer));

        CareerCoverRequest careerCoverRequest = new CareerCoverRequest(
                "companyName",
                "departmentName",
                CompanyPosition.HEAD_OF_DEPARTMENT,
                LocalDate.of(2020, 02, 01),
                LocalDate.of(2021, 02, 01)
        );

        String path = FreelancerProfileControllerPath.FREELANCER_PROFILE_CAREER_COVER.replace("{profileNum}", String.valueOf(freelancerProfile.getNum()));

        //when
        RestAssured
                .given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(new CareerCoverRequests(Arrays.asList(careerCoverRequest)))
                    .log().all()
                .when()
                    .post(path)
                .then()
                    .statusCode(HttpStatus.OK.value())
        ;

        //then
        Career career = careerRepository.findAll().get(0);
        Assertions.assertThat(career.getCompanyName()).isEqualTo(careerCoverRequest.getCompanyName());
        Assertions.assertThat(career.getDepartmentName()).isEqualTo(careerCoverRequest.getDepartmentName());
        Assertions.assertThat(career.getCompanyPosition()).isEqualTo(careerCoverRequest.getCompanyPosition());
        Assertions.assertThat(career.getCareerStartDate()).isEqualTo(careerCoverRequest.getCareerStartDate());
        Assertions.assertThat(career.getCareerEndDate()).isEqualTo(careerCoverRequest.getCareerEndDate());
    }
}
