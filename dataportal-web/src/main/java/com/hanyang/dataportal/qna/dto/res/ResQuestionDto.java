package com.hanyang.dataportal.qna.dto.res;


import com.hanyang.dataportal.qna.domain.AnswerStatus;
import com.hanyang.dataportal.user.domain.User;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ResQuestionDto {

    private Long questionId;
    private String title;
    private String content;
    private LocalDate date;
    private Integer view;
    private AnswerStatus answerStatus;
    private User user;


    public ResQuestionDto(Long questionId, String title, String content, LocalDate date, Integer view, AnswerStatus answerStatus, User user) {
        this.questionId = questionId;
        this.title = title;
        this.content = content;
        this.date = date;
        this.view = view;
        this.answerStatus = answerStatus;
        this.user = user;
    }
}
