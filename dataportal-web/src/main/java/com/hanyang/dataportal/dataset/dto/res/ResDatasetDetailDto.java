package com.hanyang.dataportal.dataset.dto.res;

import com.hanyang.dataportal.dataset.domain.Dataset;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ResDatasetDetailDto {

    private Long datasetId;
    private String title;
    private String description;
    private String organization;
    private String theme;
    private LocalDate createdDate;
    private LocalDate updateDate;
    private Integer view;
    private Integer download;
    private String resourceName;
    private String resourceUrl;
    public ResDatasetDetailDto(Dataset dataset) {
        this.datasetId = dataset.getDatasetId();
        this.title = dataset.getTitle();
        this.description = dataset.getDescription();
        this.organization = dataset.getOrganization();
        this.theme = dataset.getTheme();
        this.createdDate = dataset.getCreatedDate();
        this.updateDate = dataset.getUpdateDate();
        this.view = dataset.getView();
        this.download = dataset.getDownload();
        this.resourceName = dataset.getResource().getResourceName();
        this.resourceUrl = dataset.getResource().getResourceUrl();
    }
}
