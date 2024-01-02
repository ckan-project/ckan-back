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
    private String resourceId;

    @ManyToOne
    @JoinColumn(name = "datasetId")
    private Dataset dataset;

    @Column(length = 1000)
    private String url;

    private String type;

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }
}
