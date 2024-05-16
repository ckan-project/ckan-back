package com.hanyang.dataportal.faq.dto;

import com.hanyang.dataportal.faq.domain.Faq;
import com.hanyang.dataportal.faq.domain.FaqCategory;
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
    private FaqCategory category;



    public ResFaqDto(Faq faq) {
        this.faqId = faq.getFaqId();
        this.faqTitle = faq.getQuestion();
        this.faqContent = faq.getAnswer();
        this.category = faq.getFaqCategory();
    }

    public void setFaqId(Long faqId) {
        this.faqId = faqId;
    }

}
