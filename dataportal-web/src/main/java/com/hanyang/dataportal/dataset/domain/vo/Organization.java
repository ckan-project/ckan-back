package com.hanyang.dataportal.dataset.domain.vo;

import com.hanyang.dataportal.core.exception.NullException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor
public class Organization {

    private static final HashSet<String> organizationSet = new HashSet<>() {{
        add("소프트융합대학");
        add("공과대학");
        add("경상대학");
        add("과학기술융합대학");
        add("국제문화대학");
        add("디자인대학");
        add("약학대학");
        add("언론정보대학");
        add("예체능대학");
        add("입학처");
    }};

    @Column(name = "organization")
    @Lob
    private String value;

    public Organization(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (Objects.isNull(value)) {
            throw new NullException("조직명은 null일 수 없습니다.");
        }
        if(organizationSet.contains(value)){
            throw new NullException("지정하지 않은 조직명 입니다.");
        }

    }
}
