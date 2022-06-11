package com.example.elancer.interviewproject.repository;

import com.example.elancer.interviewproject.model.InterviewProject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewProjectRepository extends JpaRepository<InterviewProject, Long> {
}
