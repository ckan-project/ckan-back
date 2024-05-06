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

    public ResFaqDto() {

    }

    public static ResFaqDto toFaqDto(Faq faq) {
        ResFaqDto resFaqDto = new ResFaqDto();
        resFaqDto.setFaqId(faq.getFaqId());
        resFaqDto.setFaqTitle(faq.getFaqTitle());
        resFaqDto.setFaqContent(faq.getFaqContent());
        return resFaqDto;
    }
}
