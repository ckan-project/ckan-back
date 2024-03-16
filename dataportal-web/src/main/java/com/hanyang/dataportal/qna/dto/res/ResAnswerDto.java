package com.hanyang.dataportal.qna.dto.res;

import com.hanyang.dataportal.qna.domain.Answer;
import lombok.Data;

@Data
public class ResAnswerDto {
    //레포의 내용을 컨트롤러로
    private Long answerId;
    private String answerTitle;
    private String contentTitle;

    public ResAnswerDto() {
        this.answerId = answerId;
        this.answerTitle = answerTitle;
        this.contentTitle = contentTitle;
    }

    public static ResAnswerDto toDto(Answer answer) {
        ResAnswerDto resAnswerDto = new ResAnswerDto();
        resAnswerDto.setAnswerId(answer.getAnswerId());
        resAnswerDto.setAnswerTitle(answer.getAnswerTitle());
        resAnswerDto.setContentTitle(answer.getAnswerContent());
        return resAnswerDto;
    }


}
