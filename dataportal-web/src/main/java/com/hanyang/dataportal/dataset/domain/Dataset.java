package com.hanyang.dataportal.dataset.domain;

import com.hanyang.dataportal.dataset.domain.vo.Organization;
import com.hanyang.dataportal.dataset.domain.vo.Theme;
import com.hanyang.dataportal.dataset.dto.req.ReqDatasetDto;
import com.hanyang.dataportal.resource.domain.Resource;
import com.hanyang.dataportal.user.domain.Download;
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
    @Lob
    private String description;
    @Enumerated(EnumType.STRING)
    private Organization organization;
    private LocalDate createdDate;
    private LocalDate updateDate;
    private Integer view;
    private Integer download;
    @OneToOne(mappedBy = "dataset")
    private Resource resource;
    @Builder.Default
    @OneToMany(mappedBy = "dataset", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<DatasetTheme> datasetThemeList = new ArrayList<>();
    @Builder.Default
    @OneToMany(mappedBy = "dataset", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Scrap> scrapList = new ArrayList<>();
    @Builder.Default
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
    }
    public void addTheme(Theme theme){
        datasetThemeList.add(new DatasetTheme(this,theme));
    }
    public void removeTheme(){
        datasetThemeList.clear();
    }


}
