package com.hanyang.dataportal.qna.dto.res;


import com.hanyang.dataportal.qna.domain.AnswerStatus;
import com.hanyang.dataportal.qna.domain.Question;
import lombok.Data;

import java.time.LocalDate;

@Data

public class ResQuestionMyListDto {
    // 마이페이지 내에서의 나의 질문내역 리스트보기

    private Long questionId;
    private String title;
    private LocalDate date;
    private Integer view;
    private AnswerStatus answerStatus;

    public ResQuestionMyListDto() {

    }

    public ResQuestionMyListDto(Long questionId, String title, LocalDate date, Integer view, AnswerStatus answerStatus) {
        this.questionId = questionId;
        this.title = title;
        this.date = date;
        this.view = view;
        this.answerStatus = answerStatus;
    }


    public static ResQuestionDto toResQuestionMyListDto(Question question) {
    ResQuestionDto resQuestionDto = new ResQuestionDto();
    resQuestionDto.setQuestionId(question.getQuestionId());
    resQuestionDto.setTitle(question.getTitle());
    resQuestionDto.setDate(question.getDate());
    resQuestionDto.setView(question.getView());
    resQuestionDto.setAnswerStatus(question.getAnswerStatus());
    return  resQuestionDto;

    }
}
