package com.example.elancer.enterprise.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EnterpriseThumbnailResponse {
    private String thumbnail;

    public EnterpriseThumbnailResponse(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
