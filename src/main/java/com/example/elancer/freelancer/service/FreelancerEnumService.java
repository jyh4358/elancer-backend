package com.example.elancer.freelancer.service;

import com.example.elancer.freelancer.model.FreelancerWorkType;
import com.example.elancer.freelancer.model.WorkType;
import com.example.elancer.member.domain.CountryType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FreelancerEnumService {

    public List<String> findCountryNames() {
        return Arrays.stream(CountryType.values()).map(CountryType::getName).collect(Collectors.toList());
    }

    public List<String> findWorkTypes() {
        return Arrays.stream(FreelancerWorkType.values()).map(FreelancerWorkType::getDesc).collect(Collectors.toList());
    }
}
