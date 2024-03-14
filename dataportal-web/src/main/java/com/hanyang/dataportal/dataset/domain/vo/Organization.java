package com.hanyang.dataportal.dataset.domain.vo;

import com.hanyang.dataportal.core.exception.NullException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor
public class Organization {

    private static final String[] organizationList = new String[] {"소프트융합대학", "공과대학", "경상대학", "과학기술융합대학",
            "국제문화대학", "디자인대학", "약학대학", "언론정보대학", "예체능대학","입학처"};

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
        if(Arrays.stream(organizationList).noneMatch(s -> s.equals(value))){
            throw new NullException("지정하지 않은 조직명 입니다.");
        }

    }
}
