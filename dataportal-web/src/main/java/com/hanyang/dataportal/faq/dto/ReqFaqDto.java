package com.hanyang.dataportal.faq.dto;

import com.hanyang.dataportal.faq.domain.Faq;
import com.hanyang.dataportal.qna.domain.QuestionCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class ReqFaqDto {
    private QuestionCategory category;
    private String question;
    private String answer;

    public Faq toEntity() {
        return Faq.builder()
                .questionCategory(category)
                .question(question)
                .answer(answer)
                .build();
    }
}
