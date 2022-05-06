package com.example.elancer.freelancerprofile.repository;

import com.example.elancer.common.FreelancerHelper;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.model.IntroBackGround;
import com.example.elancer.freelancer.repository.FreelancerRepository;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.academic.AcademicAbility;
import com.example.elancer.freelancerprofile.model.academic.state.AcademicState;
import com.example.elancer.freelancerprofile.model.academic.state.SchoolLevel;
import com.example.elancer.freelancerprofile.model.career.Career;
import com.example.elancer.freelancerprofile.model.career.CompanyPosition;
import com.example.elancer.freelancerprofile.model.education.Education;
import com.example.elancer.freelancerprofile.model.language.Language;
import com.example.elancer.freelancerprofile.model.language.LanguageAbility;
import com.example.elancer.freelancerprofile.model.license.License;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.freelancerprofile.model.projecthistory.DevelopEnvironment;
import com.example.elancer.freelancerprofile.model.projecthistory.DevelopField;
import com.example.elancer.freelancerprofile.model.projecthistory.ProjectHistory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("h2")
/**
 * 1. @DataJpaTest는 스프링 내장 임베디드 사용이 기본값이다. (yml 파일의 설정을 명시해줘도 임베디드 디비를 사용한다)
 * 2. 그래서 @AutoConfigureTestDatabase 어노테이션을 통해 임베디드 디비를 사용하지않고 설정파일에 명시된 내용을 사용하게 한다.
 * */
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FreelancerProfileFindRepositoryTest {

    @Autowired
    private FreelancerRepository freelancerRepository;

    @Autowired
    private FreelancerProfileRepository freelancerProfileRepository;

    @Autowired
    private FreelancerProfileFindRepository freelancerProfileFindRepository;

    @DisplayName("ddd")
    @Test
    public void name() {
        //given
        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository);
        FreelancerProfile freelancerProfile = new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER);

        AcademicAbility academicAbility = AcademicAbility.createAcademicAbility(
                "고등학교",
                SchoolLevel.HIGH_SCHOOL,
                LocalDate.of(2012, 02, 01),
                LocalDate.of(2015, 02, 01),
                AcademicState.GRADUATION,
                "문과"
        );

        AcademicAbility academicAbility2 = AcademicAbility.createAcademicAbility(
                "대학교",
                SchoolLevel.UNIVERSITY,
                LocalDate.of(2015, 02, 01),
                LocalDate.of(2020, 02, 01),
                AcademicState.GRADUATION,
                "컴퓨터공학과"
        );

        Career career = Career.createCareer(
                "삼성",
                "개발팀",
                CompanyPosition.ASSISTANT_MANAGER,
                LocalDate.of(2020, 02, 01),
                LocalDate.of(2021, 02, 01)
        );

        Education education = Education.createEducation(
                "특수교육",
                "특수기관",
                LocalDate.of(2020, 02, 01),
                LocalDate.of(2021, 02, 01)
        );

        License license = License.createLicense("특수 자격증", "특수 기관", LocalDate.of(2019, 02, 22));

        Language language = Language.createLanguage("영어", LanguageAbility.MIDDLE);

        ProjectHistory projectHistory = ProjectHistory.createProjectHistory(
                "프로젝트명",
                LocalDate.of(2020, 02, 01),
                LocalDate.of(2021, 02, 01),
                "고객사명",
                "상주사명",
                DevelopField.APPLICATION,
                "backend",
                DevelopEnvironment.of(
                        "model",
                        "Ms",
                        "language",
                        "DB",
                        "Tool",
                        "통신",
                        "기타"
                ),
                "담당업무는 백엔드 개발"
        );

        String introduceName = "소개글";
        IntroBackGround introBackGround = IntroBackGround.COBALT_BLUE;
        String introduceVideoURL = "소개 영상 주소";
        String introduceContent = "소개 내용";
        freelancerProfile.coverIntroduceInFreelancer(introduceName, introBackGround, introduceVideoURL, introduceContent);

        freelancerProfile.coverAcademicAbilities(Arrays.asList(academicAbility, academicAbility2));
        freelancerProfile.coverCareers(Arrays.asList(career));
        freelancerProfile.coverEducation(Arrays.asList(education));
        freelancerProfile.coverLicense(Arrays.asList(license));
        freelancerProfile.coverLanguage(Arrays.asList(language));
        freelancerProfile.plusProjectHistory(projectHistory);

        FreelancerProfile savedProfile = freelancerProfileRepository.save(freelancerProfile);

        //when
        FreelancerProfile freelancerProfileByFetch = freelancerProfileFindRepository.findFreelancerProfileByFetch(freelancer.getNum()).get();

        //then
        Assertions.assertThat(freelancerProfileByFetch.getIntroduceName()).isEqualTo(introduceName);
        Assertions.assertThat(freelancerProfileByFetch.getIntroBackGround()).isEqualTo(introBackGround);
        Assertions.assertThat(freelancerProfileByFetch.getIntroduceVideoURL()).isEqualTo(introduceVideoURL);
        Assertions.assertThat(freelancerProfileByFetch.getIntroduceContent()).isEqualTo(introduceContent);
        Assertions.assertThat(freelancerProfileByFetch.getGreeting()).isEqualTo(freelancerProfile.getGreeting());
    }
}