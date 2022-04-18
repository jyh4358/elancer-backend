package com.example.elancer.integrate.freelancer;

import com.example.elancer.freelancer.join.controller.FreelancerJoinControllerPath;
import com.example.elancer.freelancer.join.dto.FreelancerJoinRequest;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.model.MailReceptionState;
import com.example.elancer.freelancer.model.WorkPossibleState;
import com.example.elancer.freelancer.repository.FreelancerRepository;
import com.example.elancer.integrate.common.IntegrateBaseTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("h2")
public class FreelancerIntegrateTest extends IntegrateBaseTest {

    @Autowired
    private FreelancerRepository freelancerRepository;

    @DisplayName("프리랜서 회원가입 통합테스트")
    @Test
    public void 프래랜서_회원가입_통합테스트() throws JsonProcessingException {
        //given
        FreelancerJoinRequest freelancerJoinRequest = new FreelancerJoinRequest(
                "name",
                "memberId",
                "pwd",
                "pwd",
                "email@manver.com",
                MailReceptionState.RECEPTION,
                "phone",
                WorkPossibleState.POSSIBLE,
                LocalDate.of(2021, 02, 01),
                null
        );

        //when
        RestAssured
                .given()
                    .body(objectMapper.writeValueAsString(freelancerJoinRequest))
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .log().all()
                .when()
                    .post(FreelancerJoinControllerPath.FREELANCER_JOIN)
                .then()
                    .statusCode(201);

        //then
        List<Freelancer> freelancers = freelancerRepository.findAll();
        Assertions.assertThat(freelancers).hasSize(1);
        Assertions.assertThat(freelancers.get(0).getUserId()).isEqualTo(freelancerJoinRequest.getMemberId());
        Assertions.assertThat(freelancers.get(0).getName()).isEqualTo(freelancerJoinRequest.getMemberName());
        Assertions.assertThat(freelancers.get(0).getEmail()).isEqualTo(freelancerJoinRequest.getMemberEmail());
        Assertions.assertThat(freelancers.get(0).getPhone()).isEqualTo(freelancerJoinRequest.getMemberPhone());
        Assertions.assertThat(freelancers.get(0).getMailReceptionState()).isEqualTo(freelancerJoinRequest.getMailReceptionState());
        Assertions.assertThat(freelancers.get(0).getWorkPossibleState()).isEqualTo(freelancerJoinRequest.getWorkPossibleState());
        Assertions.assertThat(freelancers.get(0).getWorkStartPossibleDate()).isEqualTo(freelancerJoinRequest.getWorkStartPossibleDate());
    }
}
