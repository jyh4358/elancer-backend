package com.example.elancer.freelancer.model.position;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "position_type")
public abstract class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long positionNum;

    @NotNull
    private String position;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public Position(String position) {
        this.position = position;
    }
}
