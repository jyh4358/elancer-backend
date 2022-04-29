package com.example.elancer.enterprise.domain;

import com.example.elancer.common.model.BasicEntity;
import com.example.elancer.enterprise.domain.enterprise.Enterprise;
import com.example.elancer.freelancer.model.Freelancer;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class HeartScrap extends BasicEntity {

//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "heart_scrap_id")
//    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enter_num")
    private Enterprise enterprise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "free_num")
    private Freelancer freelancer;

    public HeartScrap(Freelancer freelancer) {
        this.freelancer = freelancer;
    }

    public void insertEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    /**
     * todo - freelancer repository사용해야함 우선 보류
     *        FreelancerRepository -> findByAll
     *        EnterpriseRepository -> HeartScrap fetch join
     *        for (FreelancerDto freelancerDto : freelancerList)
     *              freelancerDto.setHeartScrap(enterprise
     *                      .getHeartscraps().stream().map( scrap -> scrap.getFreelancer())
     *                      .collect(Collection.class).Collectors.toList()
     *                      .contains(freelancer)
     */


}
