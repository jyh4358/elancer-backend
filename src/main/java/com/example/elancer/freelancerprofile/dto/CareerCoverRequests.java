package com.example.elancer.freelancerprofile.dto;

import com.example.elancer.freelancerprofile.model.career.CompanyPosition;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CareerCoverRequests {
    private List<CareerCoverRequest> careerCoverRequests;
}
