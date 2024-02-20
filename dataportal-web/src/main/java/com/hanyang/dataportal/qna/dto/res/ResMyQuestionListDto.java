package com.hanyang.dataportal.qna.dto.res;

import com.hanyang.dataportal.qna.domain.AnswerStatus;

import java.time.LocalDate;
import java.util.Date;

public class ResMyQuestionListDto {
    private Integer totalPage;
    private Integer totalData;
    private Long questionId;
    private String title;
    private LocalDate date;
    private Integer view;
    private AnswerStatus answerStatus;
    private static Integer paging;

    public ResMyQuestionListDto(Integer totalPage, Integer totalData, Long questionId, String title, LocalDate date, Integer view, AnswerStatus answerStatus) {
        this.totalPage = totalPage;
        this.totalData = totalData;
        this.questionId = questionId;
        this.title = title;
        this.date = date;
        this.view = view;
        this.answerStatus = answerStatus;

    }
}
