package com.example.elancer.document.freelancer;

import com.example.elancer.common.FreelancerHelper;
import com.example.elancer.document.common.DocumentBaseTest;
import com.example.elancer.freelancer.controller.FreelancerControllerPath;
import com.example.elancer.freelancer.controller.FreelancerEnumControllerPath;
import com.example.elancer.freelancer.dto.FreelancerAccountCoverRequest;
import com.example.elancer.freelancer.join.controller.FreelancerJoinControllerPath;
import com.example.elancer.freelancer.join.dto.FreelancerJoinRequest;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.model.FreelancerWorkType;
import com.example.elancer.freelancer.model.HopeWorkState;
import com.example.elancer.freelancer.model.KOSAState;
import com.example.elancer.freelancer.model.MailReceptionState;
import com.example.elancer.freelancer.model.PresentWorkState;
import com.example.elancer.freelancer.model.WorkPossibleState;
import com.example.elancer.freelancer.model.WorkType;
import com.example.elancer.freelancer.repository.FreelancerRepository;
import com.example.elancer.freelancer.repository.FreelancerWorkTypeRepository;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.member.domain.Address;
import com.example.elancer.member.domain.CountryType;
import com.example.elancer.testconfig.RestDocsConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FreelancerDocumentTest extends DocumentBaseTest {
    @Autowired
    private FreelancerWorkTypeRepository freelancerWorkTypeRepository;

    @DisplayName("프리랜서 회원가입 문서화 테스트")
    @Test
    public void 프리랜서_회원가입_문서화() throws Exception {
        //given
        FreelancerJoinRequest freelancerJoinRequest = new FreelancerJoinRequest(
                "memberName",
                "memberID",
                "password",
                "password",
                "membmer@email.com",
                MailReceptionState.NOT_RECEPTION,
                "memberPhone",
                WorkPossibleState.POSSIBLE,
                LocalDate.of(2022, 10, 10),
                null
        );

        //when && then
        mockMvc.perform(post(FreelancerJoinControllerPath.FREELANCER_JOIN)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(freelancerJoinRequest)))
                .andExpectAll(status().isCreated())
                .andDo(print())
                .andDo(document("freelancer-join",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청")
                        ),
                        requestFields(
                                fieldWithPath("memberName").type("String").description("회원 성명 필드."),
                                fieldWithPath("memberId").type("String").description("회원 아이디 필드."),
                                fieldWithPath("memberPassword").type("String").description("회원 비밀번호 필드."),
                                fieldWithPath("memberPasswordCheck").type("String").description("회원 비밀번호 확인 필드."),
                                fieldWithPath("memberEmail").type("String").description("회원 이메일 필드."),
                                fieldWithPath("mailReceptionState").type("Enum").description("회원 메일 수신여부 필드."),
                                fieldWithPath("memberPhone").type("String").description("회원 휴대폰 필드."),
                                fieldWithPath("workPossibleState").type("Enum").description("회원 업무 가능여부 필드."),
                                fieldWithPath("workStartPossibleDate").type("LocalDate").description("회원 업무가능일 필드."),
                                fieldWithPath("thumbnail").type("MultipartFile").description("회원 섬네일 필드.")
                        )
                ));
    }

    @DisplayName("프리랜서 계정 정보 수정 문서화 테스트")
    //TODO 임시 주석 jwt 구현후 진행해야 한다.
//    @Test
    public void 프래랜서_계정정보_수정_문서화() throws Exception {
        //given
        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository);

        FreelancerAccountCoverRequest freelancerAccountCoverRequest = new FreelancerAccountCoverRequest(
                "멤버이름",
                "패스워드",
                "패스워드",
                LocalDate.of(2000, 01, 01),
                "email@email.email.com",
                "010-0101-0101",
                "http://web.com",
                CountryType.KR,
                "경기도",
                "성남시",
                "중원구",
                Arrays.asList(FreelancerWorkType.ACCOUNTING, FreelancerWorkType.BIGDATA),
                null,
                null,
                9,
                5,
                400,
                600,
                KOSAState.NOT_POSSESS,
                MailReceptionState.RECEPTION,
                PresentWorkState.FREE_AT_COMPANY,
                HopeWorkState.AT_COMPANY,
                WorkPossibleState.POSSIBLE,
                LocalDate.of(2022, 02, 01),
                CountryType.KR,
                "seoul"
        );

        //when & then
        mockMvc.perform(put(FreelancerControllerPath.FREELANCER_ACCOUNT_INFO_UPDATE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(freelancerAccountCoverRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("freelancer-account-cover",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청")
                        ),
                        requestFields(
                                fieldWithPath("name").type("String").description("회원 이름 정보 필드"),
                                fieldWithPath("password").type("String").description("회원 비밀번호 정보 필드"),
                                fieldWithPath("passwordCheck").type("String").description("회원 비밀번호 확인 정보 필드"),
                                fieldWithPath("birthDate").type("LocalDate").description("회원 생년월일 정보 필드"),
                                fieldWithPath("email").type("String").description("회원 이메일 정보 필드"),
                                fieldWithPath("phone").type("String").description("회원 폰번호 정보 필드"),
                                fieldWithPath("website").type("String").description("회원 웹사이트 정보 필드"),
                                fieldWithPath("countryType").type("CountryType").description("회원 거주국가 정보 필드"),
                                fieldWithPath("zipcode").type("String").description("회원 거주지 정보 필드"),
                                fieldWithPath("mainAddress").type("String").description("회원 거주지 시동구 정보 필드"),
                                fieldWithPath("detailAddress").type("String").description("회원 거주지 읍면리 정보 필드"),
                                fieldWithPath("freelancerWorkTypes").type("List<FreelancerWorkType>").description("회원 업무분야 정보 필드"),
                                fieldWithPath("workEtcField").type("String").description("회원 업무분야 직접입력 정보 필드"),
                                fieldWithPath("careerForm").type("MultipartFile").description("회원 경력기술서 정보 필드"),
                                fieldWithPath("careerYear").type("int").description("회원 경력 년수 정보 필드"),
                                fieldWithPath("careerMonth").type("int").description("회원 경력 개월수 정보 필드"),
                                fieldWithPath("hopeMonthMinPay").type("int").description("회원 희망 월단가 최소값 정보 필드"),
                                fieldWithPath("hopeMonthMaxPay").type("int").description("회원 희망 월단가 최대값 정보 필드"),
                                fieldWithPath("kosaState").type("KOSAState").description("회원 kosa보유 여부 정보 필드"),
                                fieldWithPath("mailReceptionState").type("MailReceptionState").description("회원 메일수신 여부 정보 필드"),
                                fieldWithPath("presentWorkState").type("PresentWorkState").description("회원 현재 업무상태 정보 필드"),
                                fieldWithPath("hopeWorkState").type("HopeWorkState").description("회원 희망 업무형태 정보 필드"),
                                fieldWithPath("workPossibleState").type("WorkPossibleState").description("회원 업무가능 여부 정보 필드"),
                                fieldWithPath("workStartPossibleDate").type("LocalDate").description("회원 업무가능일 정보 필드"),
                                fieldWithPath("hopeWorkCountry").type("CountryType").description("회원 희망지역 국가 정보 필드"),
                                fieldWithPath("hopeWorkCity").type("String").description("회원 희망지역 도시 정보 필드")
                        )
                ));
    }

    @DisplayName("프리랜서 계정 정보 조회 문서화 테스트")
    @Test
    public void 프래랜서_계정정보_조회_문서화() throws Exception {
        //given
        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository);

        List<WorkType> workTypes = freelancerWorkTypeRepository.saveAll(Arrays.asList(WorkType.createWorkType(FreelancerWorkType.ACCOUNTING, freelancer), WorkType.createWorkType(FreelancerWorkType.BIGDATA, freelancer)));

        freelancer.updateFreelancer(
                "멤버이름",
                "패스워드",
                "email@email.email.com",
                "010-0101-0101",
                "http://web.com",
                new Address(CountryType.KR, "경기도", "성남시", "중원구"),
                LocalDate.of(2000, 01, 01),
                9,
                5,
                400,
                600,
                workTypes,
                null,
                KOSAState.NOT_POSSESS,
                MailReceptionState.RECEPTION,
                PresentWorkState.FREE_AT_COMPANY,
                HopeWorkState.AT_COMPANY,
                WorkPossibleState.POSSIBLE,
                LocalDate.of(2022, 02, 01),
                CountryType.KR,
                "seoul"
        );

        Freelancer updatedFreelancer = freelancerRepository.save(freelancer);

        //when & then
        mockMvc.perform(RestDocumentationRequestBuilders.get(FreelancerControllerPath.FREELANCER_ACCOUNT_INFO_FIND, updatedFreelancer.getNum())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("freelancer-account-find",
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청")
                        ),
                        responseFields(
                                fieldWithPath("name").type("String").description("회원 이름 정보 필드"),
                                fieldWithPath("birthDate").type("LocalDate").description("회원 생년월일 정보 필드"),
                                fieldWithPath("email").type("String").description("회원 이메일 정보 필드"),
                                fieldWithPath("phone").type("String").description("회원 폰번호 정보 필드"),
                                fieldWithPath("website").type("String").description("회원 웹사이트 정보 필드"),
                                fieldWithPath("countryType").type("CountryType").description("회원 거주국가 정보 필드"),
                                fieldWithPath("zipcode").type("String").description("회원 거주지 정보 필드"),
                                fieldWithPath("mainAddress").type("String").description("회원 거주지 시동구 정보 필드"),
                                fieldWithPath("detailAddress").type("String").description("회원 거주지 읍면리 정보 필드"),
                                fieldWithPath("freelancerWorkTypes").type("List<FreelancerWorkType>").description("회원 업무분야 정보 필드"),
                                fieldWithPath("workEtcField").type("String").description("회원 업무분야 직접입력 정보 필드"),
                                fieldWithPath("fileName").type("String").description("회원 파일이름 정보 필드"),
                                fieldWithPath("careerYear").type("int").description("회원 경력 년수 정보 필드"),
                                fieldWithPath("careerMonth").type("int").description("회원 경력 개월수 정보 필드"),
                                fieldWithPath("hopeMonthMinPay").type("int").description("회원 희망 월단가 최소값 정보 필드"),
                                fieldWithPath("hopeMonthMaxPay").type("int").description("회원 희망 월단가 최대값 정보 필드"),
                                fieldWithPath("kosaState").type("KOSAState").description("회원 kosa보유 여부 정보 필드"),
                                fieldWithPath("mailReceptionState").type("MailReceptionState").description("회원 메일수신 여부 정보 필드"),
                                fieldWithPath("presentWorkState").type("PresentWorkState").description("회원 현재 업무상태 정보 필드"),
                                fieldWithPath("hopeWorkState").type("HopeWorkState").description("회원 희망 업무형태 정보 필드"),
                                fieldWithPath("workPossibleState").type("WorkPossibleState").description("회원 업무가능 여부 정보 필드"),
                                fieldWithPath("workStartPossibleDate").type("LocalDate").description("회원 업무가능일 정보 필드"),
                                fieldWithPath("hopeWorkCountry").type("CountryType").description("회원 희망지역 국가 정보 필드"),
                                fieldWithPath("hopeWorkCity").type("String").description("회원 희망지역 도시 정보 필드")
                        )
                ));
    }

    @DisplayName("프리랜서 국가정보 조회 문서화 테스트")
    @Test
    public void 프래랜서_국가정보_조회_문서화() throws Exception {
        //when & then
        mockMvc.perform(RestDocumentationRequestBuilders.get(FreelancerEnumControllerPath.FREELANCER_COUNTRIES_FIND)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("freelancer-countryType-names-find",
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청")
                        ),
                        responseFields(
                                fieldWithPath("countryNames").type("List<String>").description("거주국가 이름 리스트 정보 필드")
                        )
                ));
    }

    @DisplayName("프리랜서 업무분야 조회 문서화 테스트")
    @Test
    public void 프래랜서_업무분야_조회_문서화() throws Exception {
        //when & then
        mockMvc.perform(RestDocumentationRequestBuilders.get(FreelancerEnumControllerPath.FREELANCER_WORK_TYPE_FIND)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("freelancer-worktype-names-find",
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청")
                        ),
                        responseFields(
                                fieldWithPath("workTypeNames").type("List<String>").description("업무분야 이름 리스트 정보 필드")
                        )
                ));
    }

    @AfterEach
    void tearDown() {
        databaseClean.clean();
    }
}
