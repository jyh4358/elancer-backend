package com.example.elancer.document.enterprise;

import com.example.elancer.common.EnterpriseHelper;
import com.example.elancer.document.common.DocumentBaseTest;
import com.example.elancer.enterprise.domain.enterprise.Enterprise;
import com.example.elancer.enterprise.dto.EnterpriseIntroRequest;
import com.example.elancer.enterprise.repository.EnterpriseRepository;
import com.example.elancer.testconfig.RestDocsConfiguration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EnterpriseIntroDocumentTest extends DocumentBaseTest {

    @Test
    @DisplayName("기업 프로필 문서화")
    public void 기업_프로필_문서화() throws Exception {

        //given
        Enterprise enterprise = EnterpriseHelper.기업_생성(enterpriseRepository);

        List<String> mainBizCodes = new ArrayList<>();
        mainBizCodes.add("main_biz1");
        mainBizCodes.add("main_biz2");
        mainBizCodes.add("main_biz3");
        List<String> subBizCodes = new ArrayList<>();
        subBizCodes.add("sub_biz1");
        subBizCodes.add("sub_biz2");
        subBizCodes.add("sub_biz3");


        EnterpriseIntroRequest enterpriseIntroRequest = new EnterpriseIntroRequest(
                "프로필 title",
                "SI",
                100000000,
                "123-123-123",
                mainBizCodes,
                subBizCodes
        );

        mockMvc.perform(post("/enterprise/{num}/profile".replace("{num}", String.valueOf(enterprise.getNum())))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(enterpriseIntroRequest)))
                .andExpectAll(status().isOk())
                .andDo(print())
                .andDo(document("enterprise-profile",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청")
                        ),
                        requestFields(
                                fieldWithPath("introTitle").type("String").description("프로필 타이틀 필드"),
                                fieldWithPath("bizContents").type("String").description("주요 사업 내용"),
                                fieldWithPath("sales").type("Integer").description("연간 매출액"),
                                fieldWithPath("idNumber").type("String").description("사업자 등록 번호"),
                                fieldWithPath("mainBizCodes").type("List<String>").description("사업 분야"),
                                fieldWithPath("subBizCodes").type("List<String>").description("업무 분야")
                        )
                ));
    }

}
