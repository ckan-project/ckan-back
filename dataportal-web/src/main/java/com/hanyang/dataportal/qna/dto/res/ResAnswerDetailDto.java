package com.hanyang.dataportal.qna.dto.res;

import com.hanyang.dataportal.qna.domain.Answer;
import com.hanyang.dataportal.qna.domain.Question;
import com.hanyang.dataportal.user.domain.User;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ResAnswerDetailDto {
    private Long answerId;
    private String content;
    private LocalDate date;
    private Question question;
    private User admin;

    public ResAnswerDetailDto() {

    }

    // 생성자로 바꾸세요
    public static ResAnswerDetailDto toDetailDto(Answer answer) {
     ResAnswerDetailDto resAnswerDetailDto = new ResAnswerDetailDto();
     resAnswerDetailDto.setAnswerId(answer.getAnswerId());
     resAnswerDetailDto.setContent(answer.getAnswerContent());
     resAnswerDetailDto.setDate(answer.getAnswerdate());
     resAnswerDetailDto.setQuestion(answer.getQuestion());
     resAnswerDetailDto.setAdmin(answer.getAdmin());
     return resAnswerDetailDto;
    }
}
/*   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;
    @Lob
    private String content;
    private LocalDate date;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questionId")
    private Question question;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User admin;*/