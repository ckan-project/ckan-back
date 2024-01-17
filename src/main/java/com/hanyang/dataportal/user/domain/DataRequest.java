package com.hanyang.dataportal.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DataRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dataRequestId;

    private String title;

    private String organization;

    private String content;

    private String purpose;
}
