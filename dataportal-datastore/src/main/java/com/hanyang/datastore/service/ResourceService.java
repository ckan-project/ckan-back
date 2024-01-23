package com.hanyang.datastore.service;

import com.hanyang.datastore.dto.ResourceDto;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ResourceService {

    private final MongoTemplate mongoTemplate;
    private final S3Service s3Service;
    private final ConnectionService connectionService;


    public void uploadResource(Long datasetId,MultipartFile file) throws IOException {

        ResourceDto resourceDto = s3Service.uploadFile(datasetId, file);
        connectionService.sendResourceData(datasetId,resourceDto);

    }

    public void createDataTable(String datasetId,String resourceId) {
        InputStream file = s3Service.getFile(datasetId, resourceId);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file))) {

            // OpenCSV를 사용하여 CSV 파일을 읽습니다.
            CSVReader csvReader = new CSVReader(reader);
            List<String[]> rows = csvReader.readAll();

            // 첫 번째 행을 컬럼으로, 나머지 행을 데이터로 사용합니다.
            if (!rows.isEmpty()) {
                String[] columns = rows.get(0);

                // MongoDB에 컬렉션을 생성합니다.
                if (!mongoTemplate.collectionExists(resourceId)) {
                    mongoTemplate.createCollection(resourceId);
                    System.out.println("Collection created: " + resourceId);

                    // 첫 번째 행을 제외한 나머지 행을 MongoDB에 추가합니다.
                    for (int i = 1; i < rows.size(); i++) {
                        String[] data = rows.get(i);
                        // 데이터를 MongoDB에 추가하는 로직을 추가하세요.
                        // 여기에서는 간단한 출력만 수행합니다.
                        System.out.println("Inserting data: " + String.join(", ", data));
                    }
                } else {
                    System.out.println("Collection already exists: " + resourceId);
                }
            } else {
                System.out.println("CSV file is empty.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }
}



