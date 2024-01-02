package com.hanyang.dataportal.user.domain;

import com.hanyang.dataportal.dataset.domain.Dataset;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "datasetId")
    private Dataset dataset;
}
