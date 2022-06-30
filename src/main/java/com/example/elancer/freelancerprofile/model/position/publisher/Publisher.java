package com.example.elancer.freelancerprofile.model.position.publisher;

import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.position.Position;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@DiscriminatorValue("PUBLISHER")
public class Publisher extends Position {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "publisher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PublishingSkill> publishingSkills = new ArrayList<>();

    private String etcSkill;

    public Publisher(PositionType positionType, FreelancerProfile freelancerProfile, String etcSkill) {
        super(positionType, freelancerProfile);
        this.etcSkill = etcSkill;
    }

    public static Publisher createBasicPublisher(PositionType positionType, FreelancerProfile freelancerProfile, String etcSkill) {
        return new Publisher(positionType, freelancerProfile, etcSkill);
    }

    @Override
    public List<String> getAllSkillNames() {
        List<String> allSkillNames = new ArrayList<>();
        allSkillNames.addAll(publishingSkills.stream()
                .map(publishingSkill -> publishingSkill.getPublishingDetailSkill().getDesc())
                .collect(Collectors.toList()));
        allSkillNames.add(etcSkill);
        return allSkillNames;
    }

    public void coverPublishingSkill(List<PublishingSkill> publishingSkillList) {
        this.publishingSkills.clear();
        for (PublishingSkill publishingSkill : publishingSkillList) {
            publishingSkill.setPublisher(this);
        }
        this.publishingSkills.addAll(publishingSkillList);
    }
}
