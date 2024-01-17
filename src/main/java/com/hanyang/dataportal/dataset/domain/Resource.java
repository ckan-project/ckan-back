package com.hanyang.dataportal.dataset.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Resource {

    @Id
    private String resourceId;

    @Column(length = 1000)
    private String url;

    private String type;

    @OneToMany(mappedBy = "resource", fetch = FetchType.LAZY)
    private List<Download> downloadList = new ArrayList<>();

}
