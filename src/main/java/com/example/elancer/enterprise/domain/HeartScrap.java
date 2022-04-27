package com.example.elancer.enterprise.domain;

import com.example.elancer.enterprise.domain.enterprise.Enterprise;
import com.example.elancer.freelancer.model.Freelancer;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class HeartScrap {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "heart_scr")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "num")
    private Enterprise enterprise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "num")
    private Freelancer freelancer;

    public HeartScrap(Enterprise enterprise, Freelancer freelancer) {
        this.enterprise = enterprise;
        this.freelancer = freelancer;
    }


}
