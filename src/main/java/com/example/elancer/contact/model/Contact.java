package com.example.elancer.contact.model;

import com.example.elancer.common.model.BasicEntity;
import com.example.elancer.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("Contact")
public class Contact extends BasicEntity {
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_num")
    private Member member;


    public Contact(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public static Contact of(String title, String content) {
        return new Contact(title, content);
    }

    public void updateContact(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
