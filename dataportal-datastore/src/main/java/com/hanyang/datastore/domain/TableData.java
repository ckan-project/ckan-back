package com.hanyang.datastore.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedHashMap;
import java.util.Map;

@Document
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TableData {
    @Id
    private String id;
    private Map<String, Object> attributes = new LinkedHashMap<>();

    public void setAttributes(String col,String row) {
        this.attributes.put(col,row);
    }
}
