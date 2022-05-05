package com.example.elancer.freelancer.controller;

import com.example.elancer.freelancer.dto.CountriesResponse;
import com.example.elancer.freelancer.dto.WorkTypesResponse;
import com.example.elancer.freelancer.service.FreelancerEnumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FreelancerEnumController {
    private final FreelancerEnumService freelancerEnumService;

    @GetMapping(FreelancerEnumControllerPath.FREELANCER_COUNTRIES_FIND)
    public ResponseEntity<CountriesResponse> findCountryNames() {
        CountriesResponse countriesResponse = new CountriesResponse(freelancerEnumService.findCountryNames());
        return new ResponseEntity<CountriesResponse>(countriesResponse, HttpStatus.OK);
    }

    @GetMapping(FreelancerEnumControllerPath.FREELANCER_WORK_TYPE_FIND)
    public ResponseEntity<WorkTypesResponse> findWorkTypes() {
        WorkTypesResponse workTypesResponse = new WorkTypesResponse(freelancerEnumService.findWorkTypes());
        return new ResponseEntity<WorkTypesResponse>(workTypesResponse, HttpStatus.OK);
    }
}
