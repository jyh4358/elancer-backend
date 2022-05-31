package com.example.elancer.enterprise.dto;

import com.example.elancer.enterprise.model.enterprise.Enterprise;
import com.example.elancer.enterprise.model.enterpriseintro.EnterpriseMainBiz;
import com.example.elancer.enterprise.model.enterpriseintro.EnterpriseSubBiz;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

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
    private String mainEtc;
    private List<String> subBizCodes = new ArrayList<>();
    private String subEtc;


    public static EnterpriseProfileResponse of(Enterprise enterprise) {

        String mainEtc = "";
        String subEtc = "";

        List<EnterpriseMainBiz> enterpriseMainBizs = enterprise.getEnterpriseIntro().getEnterpriseMainBizs();
        List<String> mainBizCodes = enterpriseMainBizs.stream().map(s ->
                s.getMainBusiness().getCode()).collect(Collectors.toList());
        for (EnterpriseMainBiz enterpriseMainBiz : enterpriseMainBizs) {
            if (StringUtils.hasText(enterpriseMainBiz.getEtc())) {
                mainEtc = enterpriseMainBiz.getEtc();
            }
        }


        List<EnterpriseSubBiz> enterpriseSubBizs = enterprise.getEnterpriseIntro().getEnterpriseSubBizs();
        List<String> subBizCodes = enterpriseSubBizs.stream().map(s ->
                s.getSubBusiness().getCode()).collect(Collectors.toList());
        for (EnterpriseSubBiz enterpriseSubBiz : enterpriseSubBizs) {
            if (StringUtils.hasText(enterpriseSubBiz.getEtc())) {
                subEtc = enterpriseSubBiz.getEtc();
            }
        }


        return new EnterpriseProfileResponse(
                enterprise.getEnterpriseIntro().getIntroTitle(),
                enterprise.getBizContents(),
                enterprise.getSales(),
                enterprise.getIdNumber(),
                mainBizCodes,
                mainEtc,
                subBizCodes,
                subEtc
        );
    }
}
