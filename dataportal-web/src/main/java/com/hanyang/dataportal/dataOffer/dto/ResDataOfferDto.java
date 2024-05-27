package com.hanyang.dataportal.dataOffer.dto;

import com.hanyang.dataportal.dataOffer.domain.DataOffer;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ResDataOfferDto {
    private Long dataOfferId;

    private String requestTitle;
    private String dataName;  // 공공데이터명
    private String organizationName;  // 기관명

    private LocalDate date;

    public ResDataOfferDto(DataOffer dataOffer) {
        this.dataOfferId = dataOffer.getId();

        this.dataName = dataOffer.getDataName();
        this.requestTitle = dataOffer.getRequestContent();
        this.organizationName = dataOffer.getOrganizationName();

        this.date = dataOffer.getDate();
    }

}
