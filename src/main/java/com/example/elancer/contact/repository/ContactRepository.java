package com.example.elancer.contact.repository;

import com.example.elancer.contact.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
//    @Query("select c from Contact c left join fetch c.member where c.member =: num")
    List<Contact> findByMember_num(@Param("member_num") Long num);
}
