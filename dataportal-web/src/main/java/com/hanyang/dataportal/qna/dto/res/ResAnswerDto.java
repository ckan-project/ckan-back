package com.hanyang.dataportal.qna.dto.res;

import com.hanyang.dataportal.qna.domain.Answer;
import lombok.Data;

@Data
public class ResAnswerDto {
    private Long answerId;
    private String title;
    private String content;

    public ResAnswerDto(Answer answer) {
        this.answerId = answer.getAnswerId();
        this.title = answer.getTitle();
        this.content = answer.getContent();
    }
}
