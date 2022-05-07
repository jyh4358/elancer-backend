package com.example.elancer.login.auth.dto;

import com.example.elancer.member.domain.MemberType;
import com.example.elancer.member.domain.Member;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * id : PK
 * userId : 가입 아이디
 * role : 권한
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDetails implements UserDetails{

    private Long id;
    private String userId;
    private MemberType role;


    @Builder
    public MemberDetails(Long id, String userId, MemberType role) {
        this.id = id;
        this.userId = userId;
        this.role = role;

    }

    public static MemberDetails userDetailsFrom(Member member) {
        return MemberDetails.builder()
                .id(member.getNum())
                .userId(member.getUserId())
                .role(member.getRole())
                .build();
    }

    public MemberDetails(String userid) {
        this.userId = userId;
    }

    public boolean checkPresentId() {
        return this.id != null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return role.getKey();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }




}
