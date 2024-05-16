package com.hanyang.dataportal.faq.dto;

import com.hanyang.dataportal.faq.domain.Faq;
import com.hanyang.dataportal.faq.domain.FaqCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class ReqFaqDto {
    private FaqCategory category;
    private String question;
    private String answer;

    public Faq toEntity() {
        return Faq.builder()
                .faqCategory(category)
                .question(question)
                .answer(answer)
                .build();
    }
}
