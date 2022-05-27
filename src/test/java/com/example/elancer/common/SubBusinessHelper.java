package com.example.elancer.common;

import com.example.elancer.enterprise.domain.enterpriseintro.SubBusiness;
import com.example.elancer.enterprise.repository.SubBusinessRepository;

import java.util.ArrayList;
import java.util.List;

public class SubBusinessHelper {

    public static void 업무분야_데이터_생성(SubBusinessRepository subBusinessRepository) {
        List<SubBusiness> subBusinesses = new ArrayList<>();
        subBusinesses.add(SubBusiness.builder()
                .code("sub_biz1")
                .bizName("쇼핑몰")
                .build());
        subBusinesses.add(SubBusiness.builder()
                .code("sub_biz2")
                .bizName("여행사")
                .build());
        subBusinesses.add(SubBusiness.builder()
                .code("sub_biz3")
                .bizName("금융")
                .build());
        subBusinesses.add(SubBusiness.builder()
                .code("sub_biz4")
                .bizName("증권")
                .build());
        subBusinesses.add(SubBusiness.builder()
                .code("sub_biz5")
                .bizName("카드")
                .build());
        subBusinesses.add(SubBusiness.builder()
                .code("sub_biz6")
                .bizName("보험")
                .build());
        subBusinesses.add(SubBusiness.builder()
                .code("sub_biz7")
                .bizName("병원")
                .build());
        subBusinesses.add(SubBusiness.builder()
                .code("sub_biz8")
                .bizName("대학")
                .build());
        subBusinesses.add(SubBusiness.builder()
                .code("sub_biz9")
                .bizName("공공기관")
                .build());
        subBusinesses.add(SubBusiness.builder()
                .code("sub_biz10")
                .bizName("물류")
                .build());
        subBusinesses.add(SubBusiness.builder()
                .code("sub_biz11")
                .bizName("회계")
                .build());
        subBusinesses.add(SubBusiness.builder()
                .code("sub_biz12")
                .bizName("제조")
                .build());
        subBusinesses.add(SubBusiness.builder()
                .code("sub_biz13")
                .bizName("건설")
                .build());
        subBusinesses.add(SubBusiness.builder()
                .code("sub_biz14")
                .bizName("통신")
                .build());
        subBusinesses.add(SubBusiness.builder()
                .code("sub_biz15")
                .bizName("유통")
                .build());
        subBusinesses.add(SubBusiness.builder()
                .code("sub_biz16")
                .bizName("생상")
                .build());
        subBusinesses.add(SubBusiness.builder()
                .code("sub_biz17")
                .bizName("미디어")
                .build());
        subBusinesses.add(SubBusiness.builder()
                .code("sub_biz18")
                .bizName("교육")
                .build());
        subBusinesses.add(SubBusiness.builder()
                .code("sub_biz19")
                .bizName("반도체")
                .build());
        subBusinesses.add(SubBusiness.builder()
                .code("sub_biz20")
                .bizName("자동차")
                .build());
        subBusinesses.add(SubBusiness.builder()
                .code("sub_biz21")
                .bizName("암호화폐")
                .build());
        subBusinesses.add(SubBusiness.builder()
                .code("sub_biz22")
                .bizName("블록체인")
                .build());
        subBusinesses.add(SubBusiness.builder()
                .code("sub_etc")
                .bizName("기타")
                .build());

        subBusinessRepository.saveAll(subBusinesses);

    }
}
