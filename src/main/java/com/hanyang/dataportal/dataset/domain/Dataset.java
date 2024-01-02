package com.hanyang.dataportal.dataset.domain;

import com.hanyang.dataportal.dataset.dto.req.ReqDatasetDto;
import com.hanyang.dataportal.user.domain.Follow;
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

    private LocalDate createdDate;

    private LocalDate updateDate;

    private Integer view = 0;

    private Integer download = 0;

    @OneToMany(mappedBy = "dataset", fetch = FetchType.LAZY)
    private List<Resource> resources = new ArrayList<>();

    @OneToMany(mappedBy = "dataset", fetch = FetchType.LAZY)
    private List<Theme> themes = new ArrayList<>();

    @OneToMany(mappedBy = "dataset", fetch = FetchType.LAZY)
    private List<Follow> followList = new ArrayList<>();

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

    public void setThemes(List<Theme> themes) {
        this.themes = themes;
    }


}
