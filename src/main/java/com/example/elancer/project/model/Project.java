package com.example.elancer.project.model;

import com.example.elancer.common.model.BasicEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project extends BasicEntity {

    private ProjectBackGround projectBackGround;

    //TODO 이 생성자도 임의로 일단 구현할게요. 아마 2,3곳에서만 사용될거 같은데 나중에 구현하실때 그부분만 바꿔주세요!
    public Project(ProjectBackGround projectBackGround) {
        this.projectBackGround = projectBackGround;
    }
}
