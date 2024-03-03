package com.hanyang.dataportal.qna.dto.res;

import com.hanyang.dataportal.qna.domain.Answer;
import com.hanyang.dataportal.qna.domain.AnswerStatus;
import com.hanyang.dataportal.qna.domain.Question;
import com.hanyang.dataportal.user.domain.User;
import lombok.Data;

import java.time.LocalDate;

@Data
//질문번호와 질문제목만 리턴
public class ResQuestionDto {

    private Long questionId;
    private String title;
    private String content;
    private LocalDate date;
    private Integer view;
    private AnswerStatus answerStatus;
    private User user;
    private Answer answer;

    ResQuestionDto() {

    }
    public static ResQuestionDto toQuestionDto(Question question) {
        ResQuestionDto resQuestionDto = new ResQuestionDto();
        resQuestionDto.setQuestionId(question.getQuestionId());
        resQuestionDto.setTitle(question.getTitle());
        resQuestionDto.setContent(question.getContent());
        resQuestionDto.setDate(question.getDate());
        resQuestionDto.setView(question.getView());
        resQuestionDto.setAnswerStatus(question.getAnswerStatus());
        resQuestionDto.setAnswer(question.getAnswer());
        resQuestionDto.setUser(question.getUser());
        return resQuestionDto;
    }


}
