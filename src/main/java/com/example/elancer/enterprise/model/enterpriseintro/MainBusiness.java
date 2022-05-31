package com.example.elancer.enterprise.model.enterpriseintro;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Table(name = "main_business")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MainBusiness {

    @Id
    @Column(name = "main_code", unique = true)
    private String code;

    @NotNull
    @Column(name = "biz_name")
    private String bizName;

    @Builder
    public MainBusiness(String code, String bizName) {
        this.code = code;
        this.bizName = bizName;
    }
}
