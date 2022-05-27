package com.example.elancer.enterprise.domain.enterpriseintro;

import com.example.elancer.common.model.BasicEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "enterprise_subbiz")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EnterpriseSubBiz extends BasicEntity{


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enIntro_num")
    private EnterpriseIntro enterpriseIntro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_code")
    private SubBusiness subBusiness;

    private String etc;

    @Builder
    public EnterpriseSubBiz(Long id, EnterpriseIntro enterpriseIntro, SubBusiness subBusiness) {
        this.enterpriseIntro = enterpriseIntro;
        this.subBusiness = subBusiness;
        this.etc = etc;
    }

    public static List<EnterpriseSubBiz> createList(List<SubBusiness> subBusinesses) {
        return subBusinesses.stream().map((s) ->
                        EnterpriseSubBiz.builder()
                                .subBusiness(s)
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
