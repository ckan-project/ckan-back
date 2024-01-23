package com.hanyang.dataportal.dataset.dto.res;

import com.hanyang.dataportal.dataset.domain.Dataset;
import lombok.Data;

import java.util.List;

@Data
public class ResDatasetListDto {

    private Integer totalPage;
    private Long totalElement;
    private List<SimpleDataset> simpleDatasetList;


    public ResDatasetListDto(Integer totalPage, Long totalElement, List<Dataset> datasetList) {
        this.totalPage = totalPage;
        this.totalElement = totalElement;
        this.simpleDatasetList = datasetList.stream().map(SimpleDataset::new).toList();
    }

    @Data
    public static class SimpleDataset{
        private Long datasetId;
        private String title;
        private String description;
        private Integer view;
        public SimpleDataset(Dataset dataset) {
            this.datasetId = dataset.getDatasetId();
            this.title = dataset.getTitle();
            this.description = dataset.getDescription();
            this.view = dataset.getView();
        }
    }
}
