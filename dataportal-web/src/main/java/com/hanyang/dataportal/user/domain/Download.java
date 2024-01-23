package com.hanyang.dataportal.user.domain;

import com.hanyang.dataportal.dataset.domain.Dataset;
import com.hanyang.dataportal.dataset.domain.Resource;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Download {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long downloadId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resourceId")
    private Resource resource;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "datasetId")
    private Dataset dataset;
}
