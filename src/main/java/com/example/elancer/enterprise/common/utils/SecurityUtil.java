package com.example.elancer.enterprise.common.utils;

import com.example.elancer.login.auth.dto.MemberDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static String getLoginUsername(){
        MemberDetails user = (MemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUsername();
    }

}
