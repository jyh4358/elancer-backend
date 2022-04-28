package com.example.elancer.freelancerprofile.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AcademicAbilityCoverRequests {
    @Size(max = 3)
    private List<AcademicAbilityCoverRequest> academicAbilityCoverRequests;
}
