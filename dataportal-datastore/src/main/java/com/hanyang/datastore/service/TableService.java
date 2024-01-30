package com.hanyang.datastore.service;

import com.hanyang.datastore.domain.TableData;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TableService {

    private final MongoTemplate mongoTemplate;
    private final S3Service s3Service;

    public void createDataTable(String datasetId) throws IOException {
        InputStream file = s3Service.getFile(datasetId);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file))) {

            CSVReader csvReader = new CSVReader(reader);
            List<String[]> data = csvReader.readAll();

            if (!data.isEmpty()) {
                String[] columns = data.get(0); // 예시: CSV 파일의 첫 번째 행을 컬럼 이름으로 설정

                // 나머지 행들을 List<String[]>으로 변환
                List<String[]> rows = data.subList(1, data.size());

                if (!mongoTemplate.collectionExists(datasetId)) {
                    mongoTemplate.createCollection(datasetId);
                }

                for (String[] record : rows) {
                    // DynamicData 객체 생성
                    TableData tableData = new TableData();
                    ;
                    // 동적으로 생성되는 컬럼들을 추가로 설정
                    for (int i = 0; i < columns.length; i++) {
                        tableData.setAttributes(columns[i], record[i]);
                    }
                    // MongoDB에 문서 추가
                    mongoTemplate.insert(tableData, datasetId);
                }

            }
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }
}



