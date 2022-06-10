package com.example.elancer.security;

import com.example.elancer.common.FreelancerHelper;
import com.example.elancer.common.LoginHelper;
import com.example.elancer.common.basetest.ServiceBaseTest;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.integrate.common.IntegrateBaseTest;
import com.example.elancer.member.controller.MemberController;
import com.example.elancer.member.dto.MemberLoginResponse;
import com.example.elancer.token.jwt.JwtTokenProvider;
import com.example.elancer.token.service.JwtTokenService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RefreshTokenTest extends IntegrateBaseTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private JwtTokenService jwtTokenService;

    @DisplayName("")
    @Test
    public void name() throws Exception {
        //given
        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder);
        MemberLoginResponse memberLoginResponse = LoginHelper.로그인(freelancer.getUserId(), jwtTokenService);
        Thread.sleep(6000);

        //when
        mockMvc.perform(get("/reissue")
                .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                .header(JwtTokenProvider.REFRESH_KEY, memberLoginResponse.getRefreshToken()))
                .andExpect(status().isOk());
        //then
    }

    @AfterEach
    void tearDown() {
        databaseCleaner.clean();
    }
}
