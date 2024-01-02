package com.hanyang.dataportal.dataset.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Theme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long themeId;
    private String name;
    @ManyToOne
    @JoinColumn(name = "datasetId")
    private Dataset dataset;

    public Theme(String name) {
        this.name = name;
    }
}
