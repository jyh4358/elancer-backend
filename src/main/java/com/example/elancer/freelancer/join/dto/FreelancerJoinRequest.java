package com.example.elancer.freelancer.join.dto;

import com.example.elancer.freelancer.join.exception.FreelancerCheckPasswordException;
import com.example.elancer.freelancer.model.MailReceptionState;
import com.example.elancer.freelancer.model.WorkPossibleState;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FreelancerJoinRequest {
    @NotBlank
    private String memberName;
    @NotBlank
    private String memberId;
    @NotBlank
    private String memberPassword;
    @NotBlank
    private String memberPasswordCheck;
    @Email
    private String memberEmail;
    @NotNull
    private MailReceptionState mailReceptionState;
    @NotBlank
    private String memberPhone;
    @NotNull
    private WorkPossibleState workPossibleState;
    @NotNull
    private LocalDate workStartPossibleDate;
    private MultipartFile thumbnail;
}
