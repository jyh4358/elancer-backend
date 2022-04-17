package com.example.elancer.login.auth.dto;

import com.example.elancer.member.domain.MemberType;
import com.example.elancer.member.domain.Member;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDetails implements UserDetails{

    private String id;
    private String name;
    private String password;
    private String phone;
    private String email;
    private MemberType role;

//    private Map<String, Object> attributes;

    @Builder
    public MemberDetails(String id, String name, String password, String phone, String email, MemberType role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.role = role;

//        this.attributes = attributes;
    }


    public static MemberDetails userDetailsFrom(Member member) {
        return MemberDetails.builder()
                .id(member.getUserId())
                .name(member.getName())
                .password(member.getPassword())
                .phone(member.getPhone())
                .email(member.getEmail())
                .role(member.getRole())
                .build();
    }

//    public static MemberDetails oauth2UserOf(String id, String name, String email, Map<String, Object> attributes) {
//        return MemberDetails.builder()
//                .id(id)
//                .freelancerName(name)
//                .email(email)
//                .attributes(attributes)
//                .build();
//    }


//    @Override
//    public Map<String, Object> getAttributes() {
//        return attributes;
//    }

//TODO MemberDetails에 많은 필드가 있어야되는지에 대해 이야기 나눠봐요!
    public MemberDetails(String id) {
        this.id = id;
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
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.id;
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


//    @Override
//    public String getName() {
//        return null;
//    }

    @Override
    public String toString() {
        return "MemberDetails{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
//                ", attributes=" + attributes +
                '}';
    }
}
