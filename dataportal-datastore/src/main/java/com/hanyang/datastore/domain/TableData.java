package com.hanyang.datastore.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.LinkedHashMap;

@Embeddable
@Getter
@Setter
public class TableData {
    @Id
    private String id;
    private LinkedHashMap<String,Object> data;
}
