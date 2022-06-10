package com.example.elancer.document.contact;

import com.example.elancer.common.EnterpriseHelper;
import com.example.elancer.contact.dto.ContactDeleteRequest;
import com.example.elancer.contact.dto.ContactRequest;
import com.example.elancer.contact.dto.ContactSaveRequest;
import com.example.elancer.contact.model.Contact;
import com.example.elancer.contact.repository.ContactRepository;
import com.example.elancer.document.common.DocumentBaseTest;
import com.example.elancer.enterprise.model.enterprise.Enterprise;
import com.example.elancer.integrate.enterprise.EnterpriseLoginHelper;
import com.example.elancer.member.domain.Address;
import com.example.elancer.member.domain.CountryType;
import com.example.elancer.member.dto.MemberLoginResponse;
import com.example.elancer.project.dto.ProjectDeleteRequest;
import com.example.elancer.project.model.*;
import com.example.elancer.token.jwt.JwtTokenProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ContactDocumentTest extends DocumentBaseTest {

    @Autowired
    private ContactRepository contactRepository;

    @AfterEach
    void tearDown() {
        databaseCleaner.clean();
    }

    @Test
    @DisplayName("문의 등록 GET 문서화 테스트")
    public void 문의_등록_GET_문서화() throws Exception {
        Enterprise enterprise = EnterpriseHelper.기업_생성(enterpriseRepository, passwordEncoder);
        MemberLoginResponse memberLoginResponse = EnterpriseLoginHelper.로그인(enterprise.getUserId(), jwtTokenService);

        mockMvc.perform(get("/contact-save")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("contact-save-get",
                        requestHeaders(
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt 토큰 인증 헤더 필드.")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("응답 데이터의 타입필드, 응답 객체는 JSON 형태로 응답")
                        ),
                        responseFields(
                                fieldWithPath("name").type("String").description("성명"),
                                fieldWithPath("phone").type("Integer").description("휴대폰"),
                                fieldWithPath("email").type("String").description("이메일")
                        )
                ));
    }

    @Test
    @DisplayName("문의 등록 문서화 테스트")
    public void 문의_등록_문서화() throws Exception {
        Enterprise enterprise = EnterpriseHelper.기업_생성(enterpriseRepository, passwordEncoder);
        MemberLoginResponse memberLoginResponse = EnterpriseLoginHelper.로그인(enterprise.getUserId(), jwtTokenService);

        ContactSaveRequest contactSaveRequest = new ContactSaveRequest(
                "문의 title",
                "문의 내용"
        );

        mockMvc.perform(post("/contact-save")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                        .content(objectMapper.writeValueAsString(contactSaveRequest)))
                .andExpectAll(status().isCreated())
                .andDo(print())
                .andDo(document("contact-save",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청"),
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt 토큰 인증 헤더 필드.")
                        ),
                        requestFields(
                                fieldWithPath("title").type("String").description("제목"),
                                fieldWithPath("content").type("String").description("내용")
                        )
                ));
    }

    @Test
    @DisplayName("문의 정보 수정 문서화")
    public void 문의_정보_수정_문서화() throws Exception {

        Enterprise enterprise = EnterpriseHelper.기업_생성(enterpriseRepository, passwordEncoder);
        MemberLoginResponse memberLoginResponse = EnterpriseLoginHelper.로그인(enterprise.getUserId(), jwtTokenService);

        Contact contact = new Contact("제목 title", "내용 content");
        contactRepository.save(contact);

        ContactRequest contactRequest = new ContactRequest(contact.getNum(), "변경된 제목 title", "변경된 내용 content");

        mockMvc.perform(put("/contact-cover")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                        .content(objectMapper.writeValueAsString(contactRequest)))
                .andExpectAll(status().isOk())
                .andDo(print())
                .andDo(document("contact-cover",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청"),
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt 토큰 인증 헤더 필드.")
                        ),
                        requestFields(
                                fieldWithPath("num").type("Long").description("식별자"),
                                fieldWithPath("title").type("String").description("제목"),
                                fieldWithPath("content").type("String").description("내용")
                        )
                ));
    }

    @Test
    @DisplayName("문의 리스트 조회 문서화 테스트")
    public void 문의_리스트_조회_문서화() throws Exception {
        Enterprise enterprise = EnterpriseHelper.기업_생성(enterpriseRepository, passwordEncoder);
        MemberLoginResponse memberLoginResponse = EnterpriseLoginHelper.로그인(enterprise.getUserId(), jwtTokenService);

        Contact contact1 = new Contact("제목 title1", "내용 content1");
        Contact contact2 = new Contact("제목 title2", "내용 content2");
        Contact contact3 = new Contact("제목 title3", "내용 content3");

        contact1.setMember(enterprise);
        contact2.setMember(enterprise);
        contact3.setMember(enterprise);

        List<Contact> contacts = List.of(contact1, contact2, contact3);

        contactRepository.saveAll(contacts);

        mockMvc.perform(get("/contacts")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("contacts",
                        requestHeaders(
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt 토큰 인증 헤더 필드.")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("응답 데이터의 타입필드, 응답 객체는 JSON 형태로 응답")
                        ),
                        responseFields(
                                fieldWithPath("[]").type("List<ContactResponse>").description("문의 리스트"),
                                fieldWithPath("[].num").type("Long").description("문의 식별자"),
                                fieldWithPath("[].title").type("String").description("문의 제목"),
                                fieldWithPath("[].content").type("String").description("문의 내용"),
                                fieldWithPath("[].localDate").type("LocalDate").description("문의 날짜")
                        )
                ));
    }

    @Test
    @DisplayName("문의 삭제 문서화 테스트")
    public void 문의_삭제_문서화() throws Exception{
        Enterprise enterprise = EnterpriseHelper.기업_생성(enterpriseRepository, passwordEncoder);
        MemberLoginResponse memberLoginResponse = EnterpriseLoginHelper.로그인(enterprise.getUserId(), jwtTokenService);

        Contact contact = contactRepository.save(new Contact(
                "title",
                "content"
        ));

        ContactDeleteRequest contactDeleteRequest = new ContactDeleteRequest(
                contact.getNum()
        );


        mockMvc.perform(delete("/contact-delete")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                        .content(objectMapper.writeValueAsString(contactDeleteRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("contact-delete",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청"),
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt 토큰 인증 헤더 필드.")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("응답 데이터의 타입필드, 응답 객체는 JSON 형태로 응답")
                        ),
                        requestFields(
                                fieldWithPath("contactNum").type("Long").description("프로젝트 식별자")
                        )

                ));
    }
}
