package com.example.elancer.domains.user.entity;

import com.example.elancer.domains.user.MemberType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Freelancer {

    @Id
    private String id;

    private String name;
    private String password;
    private String phone;
    private String email;

    @Enumerated(EnumType.STRING)
    private MemberType role;

    @Builder
    public Freelancer(String id, String name, String password, String phone, String email, MemberType role) {
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
