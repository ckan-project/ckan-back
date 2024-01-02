package com.hanyang.dataportal.dataset.dto.res;

import com.hanyang.dataportal.dataset.domain.Dataset;
import com.hanyang.dataportal.dataset.domain.Theme;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ResDatasetDetailDto {

    private String title;

    private String description;

    private String organization;

    private List<Theme> themes;

    private LocalDate createdDate;

    private LocalDate updateDate;

    private Integer view;

    private Integer download;

    public ResDatasetDetailDto(Dataset dataset) {
        this.title = dataset.getTitle();
        this.description = dataset.getDescription();
        this.organization = dataset.getDescription();
        this.themes = dataset.getThemes();
        this.createdDate = dataset.getCreatedDate();
        this.updateDate = dataset.getUpdateDate();
        this.view = dataset.getView();
        this.download = dataset.getDownload();
    }
}
