package com.example.elancer.enterprise.model.enterprise;

import com.example.elancer.common.model.BasicEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EnterpriseThumbnail extends BasicEntity {

    @NotNull
    private String thumbnailPath;

    @OneToOne(fetch = FetchType.LAZY)
    private Enterprise enterprise;

    public EnterpriseThumbnail(String thumbnailPath, Enterprise enterprise) {
        this.thumbnailPath = thumbnailPath;
        this.enterprise = enterprise;
    }

    public void updateThumbnailpath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public static EnterpriseThumbnail createEnterpriseThumbnail(String thumbnailPath, Enterprise enterprise) {
        return new EnterpriseThumbnail(thumbnailPath, enterprise);
    }
}
