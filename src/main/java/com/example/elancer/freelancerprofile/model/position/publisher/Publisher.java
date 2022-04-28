package com.example.elancer.freelancerprofile.model.position.publisher;

import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.position.Position;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.freelancerprofile.model.position.developer.javaskill.JavaSkill;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@DiscriminatorValue("PUBLISHER")
public class Publisher extends Position {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "publisher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PublishingSkill> publishingSkillList = new ArrayList<>();

    private String etcSkill;

    public Publisher(PositionType positionType, FreelancerProfile freelancerProfile, String etcSkill) {
        super(positionType, freelancerProfile);
        this.etcSkill = etcSkill;
    }

    public static Publisher createBasicPublisher(PositionType positionType, FreelancerProfile freelancerProfile, String etcSkill) {
        return new Publisher(positionType, freelancerProfile, etcSkill);
    }

    public void coverPublishingSkill(List<PublishingSkill> publishingSkillList) {
        this.publishingSkillList.clear();
        for (PublishingSkill publishingSkill : publishingSkillList) {
            publishingSkill.setPublisher(this);
        }
        this.publishingSkillList.addAll(publishingSkillList);
    }
}
