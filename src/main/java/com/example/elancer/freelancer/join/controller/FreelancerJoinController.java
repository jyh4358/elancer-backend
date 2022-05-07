package com.example.elancer.freelancer.join.controller;

import com.example.elancer.common.exception.ExceptionMessage;
import com.example.elancer.freelancer.join.dto.FreelancerJoinRequest;
import com.example.elancer.freelancer.join.exception.ExistUserIdException;
import com.example.elancer.freelancer.join.exception.FreelancerCheckPasswordException;
import com.example.elancer.freelancer.join.service.FreelancerJoinService;
import com.example.elancer.freelancerprofile.exception.NotExistFreelancerProfileException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class FreelancerJoinController {
    private final FreelancerJoinService freelancerJoinService;

    @PostMapping(FreelancerJoinControllerPath.FREELANCER_JOIN)
    public ResponseEntity<Void> joinFreelancer(
            @Validated @RequestBody FreelancerJoinRequest freelancerJoinRequest
    ) {
        freelancerJoinService.joinFreelancer(freelancerJoinRequest);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @ExceptionHandler({ExistUserIdException.class})
    public ResponseEntity<ExceptionMessage> existUserIdException(ExistUserIdException e) {
        log.error(e.getClass() + ": " + e.getMessage());
        return new ResponseEntity<ExceptionMessage>(ExceptionMessage.of(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({FreelancerCheckPasswordException.class})
    public ResponseEntity<ExceptionMessage> freelancerCheckPasswordException(FreelancerCheckPasswordException e) {
        log.error(e.getClass() + ": " + e.getMessage());
        return new ResponseEntity<ExceptionMessage>(ExceptionMessage.of(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
