package com.hanyang.dataportal.qna.dto.res;

import com.hanyang.dataportal.qna.domain.AnswerStatus;
import com.hanyang.dataportal.qna.domain.Question;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

@Data
@Getter
//질문번호와 질문제목만 리턴
public class ResQuestionDto {
    //    게시글번호 |  게시글제목   | 작성자   | 작성일자 | 조회수 | 답변상태
//    private int questionNum;
//    private String questionContent;
//    private User user;
//    private Date date;
//    private int view;

    private Long questionId;
    private String title;
   // private String content;
    private LocalDate date;
    private Integer view;
    private AnswerStatus answerStatus;
   // private User user;
  //  private Answer answer;

    ResQuestionDto() {

    }
    public static ResQuestionDto toQuestionDto(Question question) {
        ResQuestionDto resQuestionDto = new ResQuestionDto();
        resQuestionDto.setQuestionId(question.getId());
        resQuestionDto.setTitle(question.getTitle());
      //  resQuestionDto.setContent(question.getContent());
        resQuestionDto.setDate(question.getDate());
        resQuestionDto.setView(question.getView());
        resQuestionDto.setAnswerStatus(question.getAnswerStatus());
       // resQuestionDto.setAnswer(question.getAnswer());
       // resQuestionDto.setUser(question.getUser());
        return resQuestionDto;
    }


}
