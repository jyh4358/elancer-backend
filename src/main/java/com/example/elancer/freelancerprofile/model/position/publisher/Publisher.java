package com.example.elancer.freelancerprofile.model.position.publisher;

import com.example.elancer.freelancerprofile.model.position.Position;
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
@DiscriminatorValue("PUBLISHER")
public class Publisher extends Position {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "publisher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PublishingSkill> publishingSkillList = new ArrayList<>();

    private String etcSkill;

    public Publisher(String position, List<PublishingSkill> publishingSkillList, String etcSkill) {
        super(position);
        this.publishingSkillList = publishingSkillList;
        this.etcSkill = etcSkill;
    }
}
