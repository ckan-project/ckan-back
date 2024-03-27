package com.hanyang.dataportal.qna.dto.res;

import com.hanyang.dataportal.qna.domain.Answer;
import com.hanyang.dataportal.qna.domain.Question;
import com.hanyang.dataportal.user.domain.User;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

@Data
public class ResAnswerListDto {
    private Long answerId;
    private String answerTitle;
    private String answerContent;
    private LocalDate date;
    private Question question;
    private User admin;


    private Page page;

    public ResAnswerListDto() {

    }

    public static ResAnswerListDto toDto(Answer answer){
        ResAnswerListDto resAnswerListDto = new ResAnswerListDto();
        resAnswerListDto.setAnswerId(answer.getAnswerId());
        resAnswerListDto.setAnswerTitle(answer.getAnswerTitle());
        resAnswerListDto.setAnswerContent(answer.getAnswerContent());
        resAnswerListDto.setAdmin(answer.getAdmin());
//        resAnswerListDto.date = Question.builder(); ..질문... 날짜를 보여줘야하는데 어떻게하지?
        return resAnswerListDto;
    }
}
