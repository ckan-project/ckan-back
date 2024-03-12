package com.hanyang.datastore.repository;

import com.hanyang.datastore.domain.MetaData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetaDataRepository extends MongoRepository<MetaData,String> {
}
