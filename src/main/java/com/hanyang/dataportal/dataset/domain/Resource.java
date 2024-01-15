package com.hanyang.dataportal.dataset.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

}
