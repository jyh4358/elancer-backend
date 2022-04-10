package com.example.elancer.member.domain;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num;

    @NotNull
    private String id;
    @NotNull
    private String password;
    @NotNull
    private String name;
    private String phone;
    private String email;

    @Enumerated(EnumType.STRING)
    private MemberType role;

    @Builder
    public Member(String id, String name, String password, String phone, String email, MemberType role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.role = role;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    public String getRoleType() {
        return this.role.getType();
    }

}
