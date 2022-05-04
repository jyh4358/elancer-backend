package com.example.elancer.freelancerprofile.dtd;

import com.example.elancer.freelancerprofile.model.position.publisher.Publisher;
import com.example.elancer.freelancerprofile.model.position.publisher.PublishingDetailSkill;
import com.example.elancer.freelancerprofile.model.position.publisher.PublishingSkill;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PublisherResponse {
    private List<PublishingDetailSkill> publishingDetailSkills;
    private String etcSkill;

    public static PublisherResponse of(Publisher publisher) {
        return new PublisherResponse(
                publisher.getPublishingSkillList().stream()
                        .map(PublishingSkill::getPublishingDetailSkill)
                        .collect(Collectors.toList()),
                publisher.getEtcSkill()
        );
    }
}
