package com.example.elancer.freelancerprofile.service;

import com.example.elancer.freelancer.model.IntroBackGround;
import com.example.elancer.freelancerprofile.dto.response.FreelancerDetailResponse;
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
import com.example.elancer.freelancerprofile.model.projecthistory.DevelopEnvironment;
import com.example.elancer.freelancerprofile.model.projecthistory.DevelopField;
import com.example.elancer.freelancerprofile.model.projecthistory.ProjectHistory;
import com.example.elancer.freelancerprofile.repository.FreelancerProfileFindRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;


@ActiveProfiles("h2")
@ExtendWith(value = MockitoExtension.class)
class FreelancerProfileFindServiceTest {

    private FreelancerProfileFindService freelancerProfileFindService;

    @Mock
    private FreelancerProfileFindRepository freelancerProfileFindRepository;

    @BeforeEach
    void setUp() {
        this.freelancerProfileFindService = new FreelancerProfileFindService(freelancerProfileFindRepository);
    }

    @DisplayName("프리랜서 프로필 정보를 조회할수 있다..")
    @Test
    public void 프리랜서_프로필_조회() {
        //given
        FreelancerProfile freelancerProfile = new FreelancerProfile("greeting", null);
        Long freelancerNum = 1L;

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


        when(freelancerProfileFindRepository.findFreelancerProfileByFetch(freelancerNum)).thenReturn(Optional.of(freelancerProfile));

        //when
        freelancerProfileFindService.findDetailFreelancerProfile(freelancerNum);

        //then


    }
}