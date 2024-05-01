package com.hanyang.dataportal.faq.dto;

import com.hanyang.dataportal.faq.domain.Faq;
import lombok.Data;

@Data
public class ResFaqDto {

    private Long faqId;
    private String faqTitle;
    private String faqContent;

    public ResFaqDto(Faq faq) {
        this.faqId = faq.getFaqId();
        this.faqTitle = faq.getFaqTitle();
        this.faqContent = faq.getFaqContent();
    }

}
