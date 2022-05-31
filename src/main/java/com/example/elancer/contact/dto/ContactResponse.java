package com.example.elancer.contact.dto;

import com.example.elancer.contact.model.Contact;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ContactResponse {
    private Long num;
    private String title;
    private String content;

    public static List<ContactResponse> of(List<Contact> contacts) {
        return contacts.stream().map( s ->
                new ContactResponse(s.getNum(), s.getTitle(), s.getContent())).collect(Collectors.toList());
    }
}
