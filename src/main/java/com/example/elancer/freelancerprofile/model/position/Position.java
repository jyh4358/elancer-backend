package com.example.elancer.freelancerprofile.model.position;

import com.example.elancer.common.model.BasicEntity;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name = "position_type")
public abstract class Position extends BasicEntity {
    /*
     * @DiscriminatorColumn의 용도는 일단 부모 테이블의 데이터의 의도를 명확하게 하기 위해서임. + 조인의 편리성 -> 포지션에 값이 있는데 이 값이 어떤 자식테이블의 데이터인지 애매할떄.
     * 해당 역할은 밑의 이넘인 PositionType이 해결해주기에 우선은 생략.
     * */
    @Enumerated(EnumType.STRING)
    private PositionType positionType;

    @OneToOne(fetch = FetchType.LAZY)
    private FreelancerProfile freelancerProfile;

    public Position(PositionType positionType, FreelancerProfile freelancerProfile) {
        this.positionType = positionType;
        this.freelancerProfile = freelancerProfile;
    }

    public abstract List<String> getAllSkillNames();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(num, position.num) && createdDate == position.createdDate && updatedDate == position.updatedDate &&
                positionType == position.positionType && Objects.equals(freelancerProfile, position.freelancerProfile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(num, createdDate, updatedDate, positionType, freelancerProfile);
    }
}
