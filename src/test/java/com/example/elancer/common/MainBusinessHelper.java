package com.example.elancer.common;

import com.example.elancer.enterprise.domain.enterpriseintro.MainBusiness;
import com.example.elancer.enterprise.repository.MainBusinessRepository;

import java.util.ArrayList;
import java.util.List;

public class MainBusinessHelper {

    public static void 사업분야_데이터_생성(MainBusinessRepository mainBusinessRepository) {
        List<MainBusiness> mainBusinesses = new ArrayList<>();
        mainBusinesses.add(MainBusiness.builder()
                .code("main_biz1")
                .bizName("웹개발")
                .build());
        mainBusinesses.add(MainBusiness.builder()
                .code("main_biz2")
                .bizName("앱개발")
                .build());
        mainBusinesses.add(MainBusiness.builder()
                .code("main_biz3")
                .bizName("솔루션개발")
                .build());
        mainBusinesses.add(MainBusiness.builder()
                .code("main_biz4")
                .bizName("GIS개발")
                .build());
        mainBusinesses.add(MainBusiness.builder()
                .code("main_biz5")
                .bizName("POS개발")
                .build());
        mainBusinesses.add(MainBusiness.builder()
                .code("main_biz6")
                .bizName("SI사업")
                .build());
        mainBusinesses.add(MainBusiness.builder()
                .code("main_etc")
                .bizName("기타")
                .build());

        mainBusinessRepository.saveAll(mainBusinesses);
    }
}
