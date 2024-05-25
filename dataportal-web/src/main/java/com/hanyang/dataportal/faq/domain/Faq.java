package com.hanyang.dataportal.faq.domain;

import com.hanyang.dataportal.faq.dto.ReqFaqDto;
import com.hanyang.dataportal.qna.domain.QuestionCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Faq {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long faqId;
    @Enumerated(EnumType.STRING)
    private QuestionCategory category;
    private String question;
    private String answer;
    public void updateFaq(ReqFaqDto reqFaqDto){
        this.answer = reqFaqDto.getAnswer();
        this.question = reqFaqDto.getQuestion();
        this.category = reqFaqDto.getCategory();
    }

}
