package com.example.elancer.enterprise.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class EnterpriseIntroRequest {

    private String introTitle;

    @NotBlank
    private String bizContents;

    private Integer sales;
    @NotBlank
    private String idNumber;

    // todo - 이후에 사업자 등록증 파일 등록 구현
    private List<String> mainBizCodes = new ArrayList<>();
    private List<String> subBizCodes = new ArrayList<>();

}
