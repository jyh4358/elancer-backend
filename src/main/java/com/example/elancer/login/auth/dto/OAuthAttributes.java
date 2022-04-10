package com.example.elancer.login.auth.dto;

import com.example.elancer.member.domain.MemberType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@ToString
@Getter
@Setter
public class OAuthAttributes implements OAuth2User {

    private String id;
    private MemberType role;
    private Map<String, Object> attributes;

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
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
    public String getName() {
        return this.id;
    }

    public void setId(String test) {
        this.id = test;
    }

    @Builder
    public OAuthAttributes(String id, MemberType role, Map<String, Object> attributes) {
        this.id = id;
        this.role = role;
        this.attributes = attributes;
    }

    public static OAuthAttributes of(String id, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .id(id)
                .attributes(attributes)
                .role(MemberType.FREELANCER)
                .build();
    }
    public void setRole(MemberType role) {
        this.role = role;
    }
}
