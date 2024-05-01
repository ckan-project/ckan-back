package com.hanyang.dataportal.faq.dto;


import com.hanyang.dataportal.faq.domain.Faq;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class ResFaqListDto {
    private Integer totalPage;
    private Long totalElement;
    private List<ResFaqDto> data;

    public ResFaqListDto(Page<Faq> faq){
        this.totalPage = faq.getTotalPages();
        this.totalElement = faq.getTotalElements();
        this.data = faq.getContent().stream().map(ResFaqDto::new).toList();

    }

}
