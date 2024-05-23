package com.hanyang.dataportal.faq.dto;

import com.hanyang.dataportal.faq.domain.Faq;
import com.hanyang.dataportal.qna.domain.QuestionCategory;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter

public class ResFaqDto {

    private Long faqId;
    private String faqTitle;
    private String faqContent;
    private QuestionCategory category;
    public ResFaqDto(Faq faq) {
        this.faqId = faq.getFaqId();
        this.faqTitle = faq.getQuestion();
        this.faqContent = faq.getAnswer();
        this.category = faq.getQuestionCategory();
    }
}
