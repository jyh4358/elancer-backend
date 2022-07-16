package com.example.elancer.common.utils;

import com.example.elancer.project.model.Project;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PageUtil {

    public static <T> boolean isContentSizeGreaterThanPageSize(List<T> content, Pageable pageable) {
        return pageable.isPaged() && content.size() > pageable.getPageSize();
    }

    public static <T> List<T> subListLastContent(List<T> content, Pageable pageable) {
        return content.subList(0, pageable.getPageSize());
    }
}
