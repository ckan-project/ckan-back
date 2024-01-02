package com.hanyang.dataportal.dataset.dto.res;

import com.hanyang.dataportal.dataset.domain.Dataset;
import lombok.Data;

import java.util.List;

@Data
public class ResDatasetDto {

    private Long datasetId;
    private String title;
    private String description;
    private Integer resourceCnt;
    private List<String> types;

    public ResDatasetDto(Dataset dataset) {
        this.datasetId = dataset.getDatasetId();
        this.title = dataset.getTitle();
        this.description = dataset.getDescription();
        this.resourceCnt = dataset.getResources().size();
    }
}
