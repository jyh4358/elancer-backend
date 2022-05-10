package com.example.elancer.enterprise.dto;

import com.example.elancer.enterprise.domain.enterprise.Enterprise;
import com.example.elancer.enterprise.domain.enterpriseintro.EnterpriseIntro;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@AllArgsConstructor
public class EnterpriseProfileResponse {

    private String introTitle;
    private String bizContents;

    private Integer sales;
    private String idNumber;

    // todo - 이후에 사업자 등록증 파일 등록 구현
    private List<String> mainBizCodes = new ArrayList<>();
    private List<String> subBizCodes = new ArrayList<>();


    public static EnterpriseProfileResponse of(Enterprise enterprise) {
        List<String> mainBizCodes = enterprise.getEnterpriseIntro()
                .getEnterpriseSubBizs().stream().map((s) ->
                        s.getSubBusiness().getCode()).collect(Collectors.toList());
        List<String> subBizCodes = enterprise.getEnterpriseIntro()
                .getEnterpriseSubBizs().stream().map((s) ->
                        s.getSubBusiness().getCode()).collect(Collectors.toList());

        return new EnterpriseProfileResponse(
                enterprise.getEnterpriseIntro().getIntroTitle(),
                enterprise.getBizContents(),
                enterprise.getSales(),
                enterprise.getIdNumber(),
                mainBizCodes,
                subBizCodes
        );
    }
}
