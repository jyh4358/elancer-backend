package com.example.elancer.contact.service;

import com.example.elancer.common.checker.RightRequestChecker;
import com.example.elancer.contact.dto.ContactResponse;
import com.example.elancer.contact.dto.ContactRequest;
import com.example.elancer.contact.dto.ContactSaveRequest;
import com.example.elancer.contact.model.Contact;
import com.example.elancer.contact.repository.ContactRepository;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.member.domain.Member;
import com.example.elancer.member.exception.NotExistContactException;
import com.example.elancer.member.exception.NotExistMemberException;
import com.example.elancer.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContactService {

    private final ContactRepository contactRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void saveContact(MemberDetails memberDetails, ContactSaveRequest contactSaveRequest) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        Member member = memberRepository.findById(memberDetails.getId()).orElseThrow(NotExistMemberException::new);
        Contact contact = Contact.of(contactSaveRequest.getTitle(), contactSaveRequest.getContent());
        System.out.println("contactSaveRequest.getTitle() + contactSaveRequest.getContent() = " + contactSaveRequest.getTitle() + contactSaveRequest.getContent());
        contact.setMember(member);

        contactRepository.save(contact);
    }

    public List<ContactResponse> findContacts(MemberDetails memberDetails) {
        RightRequestChecker.checkMemberDetail(memberDetails);

        return ContactResponse.of(contactRepository.findByMember_num(memberDetails.getId()));
    }

    @Transactional
    public void coverContact(MemberDetails memberDetails, ContactRequest contactRequest) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        Contact contact = contactRepository.findById(contactRequest.getNum()).orElseThrow(NotExistContactException::new);
        contact.updateContact(contactRequest.getTitle(), contactRequest.getContent());
    }
}
