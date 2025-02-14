package com.example.elancer.enterprise.model.enterpriseintro;

import com.example.elancer.common.model.BasicEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "enterprise_mainbiz")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EnterpriseMainBiz extends BasicEntity{

//    @Id @GeneratedValue
//    @Column(name = "enterprise_mainbiz_id")
//    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enIntro_num")
    private EnterpriseIntro enterpriseIntro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_code")
    private MainBusiness mainBusiness;

    private String etc;

    @Builder
    public EnterpriseMainBiz(EnterpriseIntro enterpriseIntro, MainBusiness mainBusiness, String etc) {
        this.enterpriseIntro = enterpriseIntro;
        this.mainBusiness = mainBusiness;
        this.etc = etc;
    }

    public static List<EnterpriseMainBiz> createList(List<MainBusiness> mainBusinesses) {
        return mainBusinesses.stream().map((s) ->
                        EnterpriseMainBiz.builder()
                                .mainBusiness(s)
                                .build())
                .collect(Collectors.toList());

    }

    public void setEnterpriseIntro(EnterpriseIntro enterpriseIntro) {
        this.enterpriseIntro = enterpriseIntro;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }
}
