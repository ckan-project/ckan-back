package com.hanyang.dataportal.qna.dto.res;

import com.hanyang.dataportal.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ResRtnQuestionListDto {
    private Long questionId;
    private String title;
    private LocalDate date;
    private Integer view;
    private User user;

    public ResRtnQuestionListDto(Long questionId, String content, LocalDate date, Integer view, String name) {
    }
}
