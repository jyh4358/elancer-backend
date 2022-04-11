package com.example.elancer.member.domain;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "member_type")
public abstract class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num;

    @NotNull
    private String userId;
//    @NotNull TODO 소셜로그인으로 로그인하면 비밀번호는 따로 저장 안하지 않나요? NotNull의 필요성에 대한 의문이에요
    private String password;
    @NotNull
    private String name;
    private String phone;
    private String email;

    @Enumerated(EnumType.STRING)
    private MemberType role;

    public String getRoleKey() {
        return this.role.getKey();
    }

    public String getRoleType() {
        return this.role.getType();
    }

}
