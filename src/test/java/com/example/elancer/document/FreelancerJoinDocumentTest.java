package com.example.elancer.document;

import com.example.elancer.freelancer.join.controller.FreelancerJoinControllerPath;
import com.example.elancer.freelancer.join.dto.FreelancerJoinRequest;
import com.example.elancer.freelancer.model.MailReceptionState;
import com.example.elancer.freelancer.model.WorkPossibleState;
import com.example.elancer.testconfig.RestDocsConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("h2")
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
public class FreelancerJoinDocumentTest {
    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MockMvc mockMvc;

    @DisplayName("프리랜서 회원가입 문서화")
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
}
