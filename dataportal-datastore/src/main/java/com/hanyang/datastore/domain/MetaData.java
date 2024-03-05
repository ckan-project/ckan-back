package com.hanyang.datastore.domain;

import com.hanyang.datastore.dto.DatasetMetaDataDto;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@Getter
public class MetaData {
    private String datasetId;
    private String title;
    private String description;
    private String labelName;
    private List<String> label = new ArrayList<>();
    public void setMetaData(DatasetMetaDataDto datasetMetaDataDto) {
        this.datasetId = datasetMetaDataDto.getDatasetId();
        this.title = datasetMetaDataDto.getTitle();
        this.description = datasetMetaDataDto.getDescription();
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }
}
