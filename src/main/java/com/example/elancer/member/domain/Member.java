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

    @NotNull
    private String password;
    @NotNull
    private String name;
    private String phone;
    private String email;

    @Enumerated(EnumType.STRING)
    private MemberType role;

    public Member(String userId, String password, String name, String phone, String email, MemberType role) {
        this.userId = userId;
        this.password = password;
        this.name = name;
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
