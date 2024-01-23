package com.hanyang.dataportal.user.domain;

import com.hanyang.dataportal.dataset.domain.Dataset;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Scrap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scrapId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "datasetId")
    private Dataset dataset;
}
