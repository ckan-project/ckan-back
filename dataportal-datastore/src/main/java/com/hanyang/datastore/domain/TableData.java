package com.hanyang.datastore.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class DynamicData {
    @Id
    private String id;
}
