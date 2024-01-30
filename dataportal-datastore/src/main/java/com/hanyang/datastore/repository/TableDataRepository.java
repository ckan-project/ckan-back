package com.hanyang.datastore.repository;

import com.hanyang.datastore.domain.TableData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableDataRepository extends MongoRepository<TableData,String> {
}
