package com.example.elancer.login.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberLoginRequest {

    private String userId;
    private String password;
}
