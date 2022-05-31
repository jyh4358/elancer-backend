package com.example.elancer.enterprise.model.enterprise;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IdPhoto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPhoto_id")
    private Long id;

    private String uploadFileName;
    private String storeFileName;

    @Builder
    public IdPhoto(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
