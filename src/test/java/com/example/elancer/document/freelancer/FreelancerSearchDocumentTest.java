package com.example.elancer.document.freelancer;

import com.example.elancer.common.FreelancerHelper;
import com.example.elancer.document.common.DocumentBaseTest;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.model.HopeWorkState;
import com.example.elancer.freelancerprofile.controller.position.FreelancerPositionSearchControllerPath;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.freelancerprofile.model.position.developer.Developer;
import com.example.elancer.freelancerprofile.repository.position.developer.DeveloperRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FreelancerSearchDocumentTest extends DocumentBaseTest {

    @Autowired
    private DeveloperRepository developerRepository;

    @DisplayName("프리랜서 개발자 검색 문서화 테스트")
    @Test
    public void 프래랜서_개발자_검색_문서화테스트() throws Exception {
        //given
        Freelancer freelancer1 = FreelancerHelper.프리랜서_생성_아이디(freelancerRepository, passwordEncoder, "id1");
        freelancer1.updateFreelancer(
                freelancer1.getName(),
                freelancer1.getPassword(),
                freelancer1.getEmail(),
                freelancer1.getPhone(),
                freelancer1.getWebsite(),
                freelancer1.getAddress(),
                freelancer1.getBirthDate(),
                3,
                3,
                0,
                100,
                new ArrayList<>(),
                null,
                null,
                freelancer1.getFreelancerAccountInfo().getMailReceptionState(),
                freelancer1.getFreelancerAccountInfo().getPresentWorkState(),
                HopeWorkState.AT_HOME,
                freelancer1.getFreelancerAccountInfo().getWorkPossibleState(),
                freelancer1.getFreelancerAccountInfo().getWorkStartPossibleDate(),
                freelancer1.getFreelancerAccountInfo().getHopeWorkCountry(),
                null
        );
        freelancerRepository.save(freelancer1);
        FreelancerProfile freelancerProfile1 = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer1, PositionType.DEVELOPER));
        developerRepository.deleteById(freelancerProfile1.getPosition().getNum());
        Developer javaDeveloper1 = developerRepository.save(Developer.createBasicDeveloper(PositionType.DEVELOPER, freelancerProfile1, "java,spring", "role"));
        freelancerProfile1.coverPosition(javaDeveloper1);


        Freelancer freelancer2 = FreelancerHelper.프리랜서_생성_아이디(freelancerRepository, passwordEncoder, "id2");
        freelancer2.updateFreelancer(
                freelancer2.getName(),
                freelancer2.getPassword(),
                freelancer2.getEmail(),
                freelancer2.getPhone(),
                freelancer2.getWebsite(),
                freelancer2.getAddress(),
                freelancer2.getBirthDate(),
                6,
                3,
                0,
                100,
                new ArrayList<>(),
                null,
                null,
                freelancer2.getFreelancerAccountInfo().getMailReceptionState(),
                freelancer2.getFreelancerAccountInfo().getPresentWorkState(),
                HopeWorkState.AT_COMPANY,
                freelancer2.getFreelancerAccountInfo().getWorkPossibleState(),
                freelancer2.getFreelancerAccountInfo().getWorkStartPossibleDate(),
                freelancer2.getFreelancerAccountInfo().getHopeWorkCountry(),
                null
        );
        freelancerRepository.save(freelancer2);
        FreelancerProfile freelancerProfile2 = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer2, PositionType.DEVELOPER));
        developerRepository.deleteById(freelancerProfile2.getPosition().getNum());
        Developer javaDeveloper2 = developerRepository.save(Developer.createBasicDeveloper(PositionType.DEVELOPER, freelancerProfile2, "python,spring,django", "role"));


        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("positionType", String.valueOf(PositionType.DEVELOPER));
        data.add("majorSkillKeywords", "java");
        data.add("minorSkill", null);
        data.add("hopeWorkStates", null);
        data.add("positionWorkManShips", null);
        data.add("workArea", null);

        //when & then
        mockMvc.perform(RestDocumentationRequestBuilders.get(FreelancerPositionSearchControllerPath.FREELANCER_DEVELOPER_SEARCH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .params(data))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("developer-search",
                        requestParameters(
                                parameterWithName("positionType").description("프리랜서 포지션 검색 필드"),
                                parameterWithName("majorSkillKeywords").description("개발자 주요스킬 검색 필드"),
                                parameterWithName("minorSkill").description("개발자 기타스킬 검색 필드"),
                                parameterWithName("hopeWorkStates").description("프리랜서 근무 위치 검색 필드"),
                                parameterWithName("positionWorkManShips").description("프리랜서 숙련도 검색 필드"),
                                parameterWithName("workArea").description("프리랜서 거주 지역 검색 필드")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청")
                        ),
                        responseFields(
                                fieldWithPath("freelancerSimpleResponseList").type("List<String>").description("프리랜서 요약 정보 데이터 리스트."),
                                fieldWithPath("freelancerSimpleResponseList.[0].freelancerNum").type("Long").description("프리랜서 식별자 정보 필드."),
                                fieldWithPath("freelancerSimpleResponseList.[0].positionName").type("String").description("프리랜서 포지션이름 정보 필드."),
                                fieldWithPath("freelancerSimpleResponseList.[0].freelancerName").type("String").description("프리랜서 이름 정보 필드."),
                                fieldWithPath("freelancerSimpleResponseList.[0].introBackGround").type("IntroBackGround").description("프리랜서 프로필 배경화면 정보 필드."),
                                fieldWithPath("freelancerSimpleResponseList.[0].careerYear").type("int").description("프리랜서 경력 정보 필드."),
                                fieldWithPath("freelancerSimpleResponseList.[0].wishState").type("boolean").description("사용자의 프리랜서 스크랩여부 정보 필드."),
                                fieldWithPath("freelancerSimpleResponseList.[0].greeting").type("String").description("프리랜서 소개말 정보 필드."),
                                fieldWithPath("freelancerSimpleResponseList.[0].skills").type("List<String>").description("프리랜서 개발자 주요스킬 정보 필드."),
                                fieldWithPath("freelancerSimpleResponseList.[0].etcSkill").type("String").description("프리랜서 개발자 기타스킬 정보 필드."),
                                fieldWithPath("freelancerSimpleResponseList.[0].projectNames").type("ListL<String>").description("프리랜서 개발자 기타스킬 정보 필드.")
                        )
                        ));
    }
}
