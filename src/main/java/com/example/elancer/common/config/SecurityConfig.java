package com.example.elancer.common.config;

import com.example.elancer.login.auth.handler.UserFailureHandler;
import com.example.elancer.login.auth.handler.UserSuccessHandler;
import com.example.elancer.login.auth.service.SecurityOAuth2UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final SecurityOAuth2UserService securityOAuth2UserService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/index").permitAll()
                    .antMatchers("/member").authenticated()
                    .and()
                .formLogin()
                    .and()
                .logout()
                    .logoutSuccessUrl("/")
                    .and()
                .oauth2Login()
                .userInfoEndpoint()
                    .userService(securityOAuth2UserService)
                .and()
                .successHandler(successHandler())
                .failureHandler(failureHandler());





    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new UserSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return new UserFailureHandler();
    }


}
