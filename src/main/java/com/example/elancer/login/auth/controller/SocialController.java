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
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping
    public ModelAndView socialGoogleLogin(ModelAndView mav) {
        System.out.println("socialProperty.getUrlLogin() = " + socialProperty.getUrlLogin());
        StringBuilder loginUrl2 = new StringBuilder()
                .append(socialProperty.getUrlLogin())
                .append("?client_id=").append(socialProperty.getClientId())
                .append("&response_type=code")
                .append("&scope=email%20profile")
                .append("&redirect_uri=").append(socialProperty.getRedirect());

        mav.addObject("loginUrl2", loginUrl2);
        mav.setViewName("login");
        return mav;
    }

    // 인증 완료 후 리다이렉트 페이지
    @GetMapping(value = "/google")
    public ModelAndView redirectKakao(ModelAndView mav, @RequestParam String code) {
        mav.addObject("code", code);
        System.out.println("redirect controller 호출 =========");
        System.out.println("code = " + code);
        mav.setViewName("redirect");
        return mav;
    }

}
