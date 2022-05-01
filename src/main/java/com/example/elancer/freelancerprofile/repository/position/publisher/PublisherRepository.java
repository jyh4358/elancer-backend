package com.example.elancer.freelancerprofile.repository.position.publisher;

import com.example.elancer.freelancerprofile.model.position.publisher.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    Optional<Publisher> findByFreelancerProfileNum(Long profileNum);
}
