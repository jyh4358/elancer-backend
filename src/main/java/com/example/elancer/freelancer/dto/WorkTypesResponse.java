package com.example.elancer.freelancer.dto;

import com.example.elancer.freelancer.model.WorkType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class WorkTypesResponse {
    private List<String> workTypeNames;
}
