package com.example.elancer.enterprise.domain.enterpriseintro;

import com.sun.istack.NotNull;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Table(name = "main_business")
public class MainBusiness {

    @Id
    @Column(name = "main_code", unique = true)
    private String code;

    @NotNull
    @Column(name = "biz_name")
    private String bizName;

}
