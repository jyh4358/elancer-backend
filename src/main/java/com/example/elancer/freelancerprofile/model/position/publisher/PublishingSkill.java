package com.example.elancer.freelancerprofile.model.position.publisher;

import com.example.elancer.common.model.BasicEntity;
import com.example.elancer.freelancerprofile.model.position.developer.Developer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PublishingSkill extends BasicEntity {

    @Enumerated(EnumType.STRING)
    private PublishingDetailSkill publishingDetailSkill;

    @ManyToOne(fetch = FetchType.LAZY)
    private Publisher publisher;

    public PublishingSkill(PublishingDetailSkill publishingDetailSkill, Publisher publisher) {
        this.publishingDetailSkill = publishingDetailSkill;
        this.publisher = publisher;
    }

    public static PublishingSkill createPublishingSkill(PublishingDetailSkill publishingDetailSkill, Publisher publisher) {
        return new PublishingSkill(publishingDetailSkill, publisher);
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }
}
