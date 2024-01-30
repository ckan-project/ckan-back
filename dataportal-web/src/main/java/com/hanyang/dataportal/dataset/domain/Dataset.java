package com.hanyang.dataportal.dataset.domain;

import com.hanyang.dataportal.dataset.dto.req.ReqDatasetDto;
import com.hanyang.dataportal.user.domain.Download;
import com.hanyang.dataportal.user.domain.Scrap;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Dataset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long datasetId;
    private String title;
    @Lob
    private String description;
    private String organization;
    private String theme;
    private LocalDate createdDate;
    private LocalDate updateDate;
    private Integer view;
    private Integer download;
    @OneToOne(mappedBy = "dataset")
    private Resource resource;
    @OneToMany(mappedBy = "dataset", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Scrap> scrapList = new ArrayList<>();
    @OneToMany(mappedBy = "dataset", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Download> downloadList = new ArrayList<>();
    @PrePersist
    public void onPrePersist() {
        createdDate = LocalDate.now();
    }
    @PreUpdate
    public void onPreUpdate() {
        updateDate = LocalDate.now();
    }
    public void updateView(Integer view) {
        this.view = view;
    }
    public void updateDataset(ReqDatasetDto reqDatasetDto){
       this.title = reqDatasetDto.getTitle();
       this.description = reqDatasetDto.getDescription();
       this.organization = reqDatasetDto.getOrganization();
       this.theme = reqDatasetDto.getTheme();
    }
}
