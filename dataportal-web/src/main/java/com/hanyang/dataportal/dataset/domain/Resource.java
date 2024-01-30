package com.hanyang.dataportal.dataset.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resourceId;
    @Lob
    private String resourceUrl;
    private String type;
    private String resourceName;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "datasetId")
    private Dataset dataset;

    public void updateResource(String url, String type,String fileName) {
        this.resourceUrl = url;
        this.type = type;
        this.resourceName = fileName;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }
}
