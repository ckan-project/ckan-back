package com.hanyang.dataportal.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Resource")
public class Resource {

    @Id
    @Column(nullable = false)
    private String resource_key;
    @Column
    private String datasetId;
    @Column
    private String url;
    @Column
    private String type;

}
