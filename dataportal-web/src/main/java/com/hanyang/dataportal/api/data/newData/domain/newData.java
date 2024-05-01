package com.hanyang.dataportal.api.data.newData.domain;

public class newData {

    // 프론트에 보여지는 newData
    // 1. 타이틀제목 2. 파일형태 3. 공개조직 4. 관련 이미지


    private String newDataTitle;

}

//@Entity
//@Builder
//@Getter
//@AllArgsConstructor
//@NoArgsConstructor
//public class Dataset {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long datasetId;
//    private String title;
//    @Lob
//    private String description;
//    private Organization organization;
//    private LocalDate createdDate;
//    private LocalDate updateDate;
//    private Integer view;
//    private Integer download;
//    @OneToOne(mappedBy = "dataset")
//    private Resource resource;
//    @Builder.Default
//    @OneToMany(mappedBy = "dataset", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
//    private List<DatasetTheme> datasetThemeList = new ArrayList<>();
//    @Builder.Default
//    @OneToMany(mappedBy = "dataset", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
//    private List<Scrap> scrapList = new ArrayList<>();
//    @Builder.Default
//    @OneToMany(mappedBy = "dataset", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
//    private List<Download> downloadList = new ArrayList<>();
//    @PrePersist
//    public void onPrePersist() {
//        createdDate = LocalDate.now();
//    }
//    @PreUpdate
//    public void onPreUpdate() {
//        updateDate = LocalDate.now();
//    }
//    public void updateView(Integer view) {
//        this.view = view;
//    }
//    public void updateDataset(ReqDatasetDto reqDatasetDto){
//        this.title = reqDatasetDto.getTitle();
//        this.description = reqDatasetDto.getDescription();
//        this.organization = reqDatasetDto.getOrganization();
//    }
//    public void addTheme(Theme theme){
//        datasetThemeList.add(new DatasetTheme(this,theme));
//    }
//    public void removeTheme(){
//        datasetThemeList.clear();
//    }
//
//}

