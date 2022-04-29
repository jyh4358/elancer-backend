package com.example.elancer.enterprise.domain.enterprise;

import com.example.elancer.enterprise.domain.HeartScrap;
import com.example.elancer.enterprise.domain.enterpriseintro.EnterpriseIntro;
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
import java.util.stream.Collectors;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("ENTERPRISE")
public class Enterprise extends Member {

    /**
     * companyName : 회사명
     * companyPeople : 회사 인원수
     * position : 담당자명
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

    private String website;
    @NotNull
    @Embedded
    private Address address;
    @NotNull
    private String bizContents;

    private int sales;
    @NotNull
    @Column(name = "idNumber")
    private String idNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPhoto_id")
    private IdPhoto idPhoto;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "enDetails_id", unique = true)
    private EnterpriseIntro enterpriseIntro;

    @OneToMany(mappedBy = "enterprise", cascade = CascadeType.ALL)
    private List<HeartScrap> heartScraps = new ArrayList<>();


    @Builder
    public Enterprise(String userId, String password, String name, String phone, String email, MemberType role, String companyName, int companyPeople, String position, String telNumber, String website, Address address, String bizContents, int sales, String idNumber, IdPhoto idPhoto) {
        super(userId, password, name, phone, email, role);
        this.companyName = companyName;
        this.companyPeople = companyPeople;
        this.position = position;
        this.telNumber = telNumber;
        this.website = website;
        this.address = address;
        this.bizContents = bizContents;
        this.sales = sales;
        this.idNumber = idNumber;
        this.idPhoto = idPhoto;
    }

    public void updateIntro(EnterpriseIntro enterpriseIntro, String bizContents, int sales, String idNumber) {
        this.enterpriseIntro = enterpriseIntro;
        this.bizContents = bizContents;
        this.sales = sales;
        this.idNumber = idNumber;

    }


    public void addHeartScrap(HeartScrap heartScrap) {
        heartScraps.add(heartScrap);
        heartScrap.insertEnterprise(this);
    }

}
