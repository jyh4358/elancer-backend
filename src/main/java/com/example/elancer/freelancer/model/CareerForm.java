package com.example.elancer.freelancer.model;

import com.example.elancer.common.model.BasicEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CareerForm extends BasicEntity {

    private String fileName;
    private String filePath;

    @OneToOne(fetch = FetchType.LAZY)
    private Freelancer freelancer;

    public CareerForm(String fileName, String filePath, Freelancer freelancer) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.freelancer = freelancer;
    }

    public static CareerForm createCareerForm(String fileName, String filePath, Freelancer freelancer) {
        return new CareerForm(fileName, filePath, freelancer);
    }
}
