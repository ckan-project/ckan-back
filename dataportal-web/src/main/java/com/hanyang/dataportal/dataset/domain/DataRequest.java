package com.hanyang.dataportal.dataset.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class DataRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dataRequestId;
    private String title;
    private String organization;
    @Lob
    private String content;
    private String purpose;
    
}
