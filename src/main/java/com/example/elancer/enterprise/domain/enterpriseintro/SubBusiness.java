package com.example.elancer.enterprise.domain.enterpriseintro;

import com.sun.istack.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sub_business")
public class SubBusiness {

    @Id
    @Column(name = "sub_code", unique = true)
    private String code;
    @NotNull
    @Column(name = "biz_name")
    private String bizName;
}
