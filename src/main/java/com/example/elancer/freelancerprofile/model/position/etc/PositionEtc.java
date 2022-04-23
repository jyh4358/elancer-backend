package com.example.elancer.freelancerprofile.model.position.etc;

import com.example.elancer.freelancerprofile.model.position.Position;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("ETC")
public class PositionEtc extends Position {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "positionEtc", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EtcRole> planFields = new ArrayList<>();

    private String etcField;

}
