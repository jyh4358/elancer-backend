package com.example.elancer.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OAuthControllerTest {


    @Autowired
    private MockMvc mvc;


    @Test
    @DisplayName("모든 유저 접근 가능")
    @WithAnonymousUser
    public void index_anonymous() throws Exception {
        mvc.perform(get("/index"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("권한을 갖는 유저만 접근 가능")
    @WithMockUser(roles = {"FREELANCER", "ENTERPRISE"})
    public void member_user() throws Exception {
        mvc.perform(get("/member"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("권한을 없는 유저는 로그인 페이지로 이동")
    @WithAnonymousUser
    public void member() throws Exception {
        mvc.perform(get("/member"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

//    @Test
//    @DisplayName("소셜로그인")
//    public void loginTest() throws Exception {
//        mvc.perform(get("/login")
//                .with(oauth2Login()
//                        .authorities(new SimpleGrantedAuthority("FREELANCER"))
//                        .attributes(attributes -> {
//                            attributes.put("username", "testid");
//                            attributes.put("name", "testname");
//                            attributes.put("email", "test@gmail.com");
//                            attributes.put("picture", "https://test");
//                        })
//                ))
//                .andExpect(status().isOk());
//    }



}
