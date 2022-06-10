package com.example.elancer.contact.controller;

import com.example.elancer.contact.dto.*;
import com.example.elancer.contact.service.ContactService;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.member.dto.MemberContactResponse;
import com.example.elancer.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;
    private final MemberService memberService;

    @GetMapping("/contact-save")
    public ResponseEntity<MemberContactResponse> findMemberContact(@AuthenticationPrincipal MemberDetails memberDetails) {

        MemberContactResponse memberContact = memberService.findMemberContact(memberDetails);
        return new ResponseEntity<>(memberContact, HttpStatus.OK);
    }

    @PostMapping("/contact-save")
    public ResponseEntity<Void> saveContact(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody ContactSaveRequest contactSaveRequest) {

        contactService.saveContact(memberDetails, contactSaveRequest);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/contacts")
    public ResponseEntity<List<ContactResponse>> findContactList(@AuthenticationPrincipal MemberDetails memberDetails) {

        List<ContactResponse> contacts = contactService.findContacts(memberDetails);
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    @PutMapping("/contact-cover")
    public ResponseEntity<Void> coverContact(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody ContactRequest contactRequest) {
        contactService.coverContact(memberDetails, contactRequest);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/contact-delete")
    public ResponseEntity<Void> deleteContact(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody ContactDeleteRequest contactDeleteRequest
    ){
        contactService.deleteContact(memberDetails, contactDeleteRequest);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
