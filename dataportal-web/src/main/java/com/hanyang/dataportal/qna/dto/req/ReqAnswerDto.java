package com.hanyang.dataportal.qna.dto.req;

import com.hanyang.dataportal.qna.domain.Answer;
import com.hanyang.dataportal.qna.domain.Question;
import com.hanyang.dataportal.user.domain.User;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Setter

public class ReqAnswerDto {
    private Question questionId;
    private Long answerId;
    private String answerTitle;
    private String answerContent;
    private LocalDate answerDate;
    private User user;

    public Answer toEntity(){
        return Answer.builder()
                .question(questionId)
                .answerId(answerId)
                .answerTitle(answerTitle)
                .answerContent(answerContent)
                .answerdate(LocalDate.now())
                .build();
    }
}