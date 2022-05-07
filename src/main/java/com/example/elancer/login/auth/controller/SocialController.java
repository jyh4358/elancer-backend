package com.example.elancer.login.auth.controller;

import com.example.elancer.token.jwt.JwtTokenProvider;
import com.example.elancer.login.auth.property.SocialProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
@RequestMapping("/social/login")
public class SocialController {

    private final SocialProperty socialProperty;


    // todo - 수정해야 할 부분. 우선 구글 접근 권한 토큰을 받아오는 주소를 내려주는 방식
    @GetMapping
    public ModelAndView socialGoogleLogin(ModelAndView mav) {

        StringBuilder loginUrl = new StringBuilder()
                .append(socialProperty.getUrlLogin())
                .append("?client_id=").append(socialProperty.getClientId())
                .append("&response_type=code")
                .append("&scope=email%20profile")
                .append("&redirect_uri=").append(socialProperty.getRedirect());

        mav.addObject("loginUrl", loginUrl);
        mav.setViewName("login");
        return mav;
    }

    // 인증 완료 후 리다이렉트 페이지
    @GetMapping(value = "/google")
    public ModelAndView redirect(ModelAndView mav, @RequestParam String code) {
        mav.addObject("code", code);
        System.out.println("code = " + code);
        mav.setViewName("redirect");
        return mav;
    }

}
