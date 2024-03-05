package com.hanyang.datastore.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@NoArgsConstructor
public class Content {
    @Id
    private String datasetId;
    //atlas search로 indexing
    private String content;

    public Content(String datasetId, String content) {
        this.datasetId = datasetId;
        this.content = content;
    }
}
