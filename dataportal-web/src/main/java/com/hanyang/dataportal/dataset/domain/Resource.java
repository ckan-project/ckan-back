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
    @Lob
    private String url;
    private String type;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "datasetId")
    private Dataset dataset;
}
