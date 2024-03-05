package com.hanyang.datastore.repository;

import com.hanyang.datastore.domain.Content;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContentRepository extends MongoRepository<Content,String> {
    Optional<Content> findByDatasetId(String datasetId);
}
