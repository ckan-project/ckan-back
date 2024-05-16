package com.hanyang.dataportal.faq.dto;


import com.hanyang.dataportal.faq.domain.Faq;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter

public class ResFaqListDto {
    private Integer totalPage;
    private Long totalElement;
    private List<SimpleFaq> data;

    public ResFaqListDto(Page<Faq> faq){
        this.totalPage = faq.getTotalPages();
        this.totalElement = faq.getTotalElements();
        this.data = faq.getContent().stream().map(SimpleFaq::new).toList();
    }

    @Data
    public static class SimpleFaq {
        private Long faqId;
        private String faqTitle;
        private String faqContent;
        
        public SimpleFaq(Faq faq) {
            this.faqId = faq.getFaqId();
            this.faqTitle = faq.getQuestion();
            this.faqContent = faq.getAnswer();
        }
    }

}
