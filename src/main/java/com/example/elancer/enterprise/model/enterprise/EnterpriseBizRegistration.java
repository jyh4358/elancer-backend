package com.example.elancer.enterprise.model.enterprise;

import com.example.elancer.common.model.BasicEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EnterpriseBizRegistration extends BasicEntity {

    @NotNull
    private String filePath;

    @OneToOne(fetch = FetchType.LAZY)
    private Enterprise enterprise;


    public EnterpriseBizRegistration(String filePath, Enterprise enterprise) {
        this.filePath = filePath;
        this.enterprise = enterprise;
    }

    public static EnterpriseBizRegistration createEnterpriseBizRegistration(String filePath, Enterprise enterprise) {
        return new EnterpriseBizRegistration(filePath, enterprise);
    }

    public void updateBizRegistration(String filePath) {
        this.filePath = filePath;
    }
}
