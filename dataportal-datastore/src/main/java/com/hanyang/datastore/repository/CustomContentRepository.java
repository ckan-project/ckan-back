package com.hanyang.datastore.repository;

import com.hanyang.datastore.domain.Content;
import com.hanyang.datastore.domain.MetaData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomContentRepository {
    private final MongoTemplate mongoTemplate;


    public String searchContent(String keyword){
        String query = "{" +
                "  \"$search\": {" +
                "    \"index\": \"search_index\"," +
                "    \"text\": {" +
                "      \"query\": \""+keyword+"\"," +
                "      \"path\": {" +
                "        \"wildcard\": \"*\"" +
                "      }" +
                "    }" +
                "  }" +
                "}";

        AggregationOperation operation = Aggregation.stage(query);
        Aggregation aggregation = Aggregation.newAggregation(operation);
        List<Content> results = mongoTemplate.aggregate(aggregation, "content", Content.class).getMappedResults();
        if(results.isEmpty()){
            return  "해당 데이터는 존재하지 않아요";
        }
        else {
            Content result = results.get(0);
            MetaData metaData = mongoTemplate.find(new Query().limit(1), MetaData.class, result.getDatasetId()).get(0);
            String msg = keyword+"와(과) 가장 유사한 데이터셋 이에요.\n"+
                     metaData.getTitle()+":\n"+
                    "http://hanyang-erica.com/dataset/";
            return msg+results.get(0).getDatasetId();
        }
    }
}
