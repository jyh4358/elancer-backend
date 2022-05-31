package com.example.elancer.wishfreelancer.model;

import com.example.elancer.common.model.BasicEntity;
import com.example.elancer.enterprise.model.enterprise.Enterprise;
import com.example.elancer.freelancer.model.Freelancer;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@NoArgsConstructor
public class WishFreelancer extends BasicEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enter_num")
    private Enterprise enterprise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "free_num")
    private Freelancer freelancer;

    public WishFreelancer(Enterprise enterprise, Freelancer freelancer) {
        this.enterprise = enterprise;
        this.freelancer = freelancer;
    }

    public static WishFreelancer createWishFreelancer(Enterprise enterprise, Freelancer freelancer) {
        return new WishFreelancer(enterprise, freelancer);
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