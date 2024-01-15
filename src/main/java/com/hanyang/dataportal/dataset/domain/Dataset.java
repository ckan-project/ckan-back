package com.hanyang.dataportal.dataset.domain;

import com.hanyang.dataportal.dataset.dto.req.ReqDatasetDto;
import com.hanyang.dataportal.user.domain.Scrap;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String description;
    private String organization;
    private String theme;
    private LocalDate createdDate;
    private LocalDate updateDate;
    private Integer view;
    private Integer download;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_id")
    private Resource resource;

    @OneToMany(mappedBy = "dataset", fetch = FetchType.LAZY)
    private List<Scrap> scrapList = new ArrayList<>();

    @PrePersist
    public void onPrePersist() {
        createdDate = LocalDate.now();
    }

    @PreUpdate
    public void onPreUpdate() {
        updateDate = LocalDate.now(); // 엔티티가 업데이트될 때 호출
    }

    public void updateView(Integer view) {
        this.view = view;
    }

    public void updateDownload(Integer download) {
        this.download = download;
    }

    public void updateDataset(ReqDatasetDto reqDatasetDto){
       this.title = reqDatasetDto.getTitle();
       this.description = reqDatasetDto.getDescription();
       this.organization = reqDatasetDto.getOrganization();
    }
}
