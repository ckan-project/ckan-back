package com.hanyang.dataportal.user.dto.req;

import lombok.Data;

@Data
public class ReqScrapDto {
    private Long datasetId;

    public ReqScrapDto(Long datasetId) {
        this.datasetId = datasetId;
    }
}
