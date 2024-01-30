package com.hanyang.datastore.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Document
public class TableData {
    @Id
    private String id;
    private Map<String, Object> attributes = new HashMap<>();

    public void setAttributes(String col,String row) {
        this.attributes.put(col,row);
    }
}
