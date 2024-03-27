package com.hanyang.dataportal.qna.dto.res;


import com.hanyang.dataportal.qna.domain.AnswerStatus;
import com.hanyang.dataportal.qna.domain.Question;
import lombok.Data;

import java.time.LocalDate;

@Data

public class ResQuestionListDto {
    /* 게시글 번호, 타이틀, 상태, 작성자, 작성일, 조회수 */
    private  Long id;
    private  String title;
    private  AnswerStatus status;
    private  String user;
    private  LocalDate date;
    private  Integer view;

    //기본 생성자
    public ResQuestionListDto() {

    }

    // setter 방식은 @Data를 사용하면 이미 생성되므로, 직접 호출을 할 필요는 없다...
    public static ResQuestionListDto toDto(Question question) {
    ResQuestionListDto resQuestionListDto = new ResQuestionListDto();
    resQuestionListDto.setId(question.getId());
    resQuestionListDto.setTitle(question.getTitle());
    resQuestionListDto.setUser(question.getUser().getName());
    resQuestionListDto.setDate(question.getDate());
    resQuestionListDto.setStatus(question.getAnswerStatus());
    resQuestionListDto.setView(question.getView());
        return resQuestionListDto;
    }


}
