package com.hanyang.dataportal.user.dto.res;

import com.hanyang.dataportal.dataset.domain.Dataset;
import com.hanyang.dataportal.user.domain.Scrap;
import lombok.Data;

@Data
public class ResScrapDto {
    /**
     * scrap id
     */
    private Long id;
    /**
     * 스크랩한 dataset
     */
    private Long datasetId;
    /**
     * 스크랩한 dataset의 제목
     */
    private String title;
    /**
     * 스크랩한 dataset의 설명
     */
    private String description;
    /**
     * 스크랩한 dataset의 리소스 타입
     */
    private String type;
    /**
     * 스크랩한 dataset의 조직
     */
    private String organization;


    public ResScrapDto(Scrap scrap) {
        this.id = scrap.getScrapId();
        this.datasetId = scrap.getDataset().getDatasetId();
        this.title = scrap.getDataset().getTitle();
        this.description = scrap.getDataset().getDescription();
//        this.type = scrap.getDataset().getResource().getType();
        this.organization = scrap.getDataset().getOrganization();
    }
}
