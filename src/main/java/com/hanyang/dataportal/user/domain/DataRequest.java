package com.hanyang.dataportal.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "DataRequest")
public class DataRequest {

    @Id
    private String dataRequestId;
    private String title;
    private String organization;
    private String content;
    private String purpose;

}
