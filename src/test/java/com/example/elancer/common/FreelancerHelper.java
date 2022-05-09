package com.example.elancer.common;

import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.model.MailReceptionState;
import com.example.elancer.freelancer.model.WorkPossibleState;
import com.example.elancer.freelancer.repository.FreelancerRepository;
import com.example.elancer.member.domain.Address;
import com.example.elancer.member.domain.CountryType;
import com.example.elancer.member.domain.MemberType;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

public class FreelancerHelper {

    public static Freelancer 프리랜서_생성(FreelancerRepository freelancerRepository, PasswordEncoder passwordEncoder) {
        Freelancer freelancer = Freelancer.createFreelancer(
                "memberId",
                passwordEncoder.encode("pwd"),
                "name",
                "phone",
                "email",
                "website",
                new Address(CountryType.KR, "zipcode","address1", "address2"),
                MemberType.FREELANCER,
                MailReceptionState.RECEPTION,
                WorkPossibleState.POSSIBLE,
                LocalDate.of(2021, 02, 01)
        );

        return freelancerRepository.save(freelancer);
    }

    public static Freelancer 프리랜서_생성_아이디(FreelancerRepository freelancerRepository, PasswordEncoder passwordEncoder, String id) {
        Freelancer freelancer = Freelancer.createFreelancer(
                id,
                passwordEncoder.encode("pwd"),
                "name",
                "phone",
                "email",
                "website",
                new Address(CountryType.KR, "zipcode","address1", "address2"),
                MemberType.FREELANCER,
                MailReceptionState.RECEPTION,
                WorkPossibleState.POSSIBLE,
                LocalDate.of(2021, 02, 01)
        );

        return freelancerRepository.save(freelancer);
    }
}

