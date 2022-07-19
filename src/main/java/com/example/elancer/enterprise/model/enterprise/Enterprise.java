package com.example.elancer.enterprise.model.enterprise;

import com.example.elancer.enterprise.model.HeartScrap;
import com.example.elancer.enterprise.model.enterpriseintro.EnterpriseIntro;
import com.example.elancer.member.domain.Address;
import com.example.elancer.member.domain.Member;
import com.example.elancer.member.domain.MemberType;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("ENTERPRISE")
public class Enterprise extends Member {

    /**
     * companyName : 회사명
     * companyPeople : 회사 인원수
     * position : 직책
     * telNumber : 담당자 휴대폰
     * website : 웹사이트
     * address : 주소
     * bizContents : 주요 사업내용
     * sales : 연간 매출액
     * idNumber : 사업자 등록번호
     * idPhoto : 사업자 등록증
     */
    // TODO 기본 이미지는 Member?, 파일 관리은 한테이블에서?
    @NotNull
    private String companyName;
    @NotNull
    private int companyPeople;
    private String position;
    @NotNull
    private String telNumber; // 담당자 전좌번호

    @NotNull
    private String bizContents;

    private Long sales;
    @NotNull
    private String idNumber;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "enterprise", cascade = CascadeType.ALL, orphanRemoval = true)
    private EnterpriseThumbnail enterpriseThumbnail;

    @OneToOne(fetch = LAZY, mappedBy = "enterprise", cascade = CascadeType.ALL, orphanRemoval = true)
    private EnterpriseBizRegistration enterpriseBizRegistration;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "enDetails_id", unique = true)
    private EnterpriseIntro enterpriseIntro;

    @OneToMany(mappedBy = "enterprise", cascade = CascadeType.ALL)
    private List<HeartScrap> heartScraps = new ArrayList<>();


    @Builder
    public Enterprise(String userId, String password, String name,
                      String phone, String email, MemberType role,
                      String companyName, int companyPeople, String position,
                      String telNumber, String website, Address address,
                      String bizContents, Long sales, String idNumber, EnterpriseThumbnail enterpriseThumbnail) {

        super(userId, password, name, phone, email, website, address, role);
        this.companyName = companyName;
        this.companyPeople = companyPeople;
        this.position = position;
        this.telNumber = telNumber;
        this.bizContents = bizContents;
        this.sales = sales;
        this.idNumber = idNumber;
        this.enterpriseThumbnail = enterpriseThumbnail;
    }

    public void initialIntro(EnterpriseIntro enterpriseIntro) {
        this.enterpriseIntro = enterpriseIntro;
    }

    public void updateEnterprise(String password, String name,
                                 String phone, String email,
                                 String companyName, int companyPeople, String position,
                                 String telNumber, String website, Address address,
                                 String bizContents, Long sales, String idNumber) {
        updateMember(name, password, email, phone, website, address);
        this.companyName = companyName;
        this.companyPeople = companyPeople;
        this.position = position;
        this.telNumber = telNumber;
        this.bizContents = bizContents;
        this.sales = sales;
        this.idNumber = idNumber;

    }


    public void updateIntro(EnterpriseIntro enterpriseIntro, String bizContents, Long sales, String idNumber) {
        this.enterpriseIntro = enterpriseIntro;
        this.bizContents = bizContents;
        this.sales = sales;
        this.idNumber = idNumber;

    }

    public void updateEnterpriseInfo(String companyName,
                                     String name,
                                     String position,
                                     String phone,
                                     String telNumber,
                                     String email
    ) {
        updateInfo(name, phone, email);
        this.companyName = companyName;
        this.position = position;
        this.telNumber = telNumber;
    }


    public void addHeartScrap(HeartScrap heartScrap) {
        heartScraps.add(heartScrap);
        heartScrap.insertEnterprise(this);
    }


}
