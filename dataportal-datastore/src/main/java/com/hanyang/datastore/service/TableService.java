package com.hanyang.datastore.service;

import com.hanyang.dataportal.core.exception.ResourceNotFoundException;
import com.hanyang.dataportal.core.global.reponse.ResponseMessage;
import com.hanyang.datastore.domain.TableData;
import com.hanyang.datastore.dto.ResTableDto;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TableService {

    private final MongoTemplate mongoTemplate;
    private final S3Service s3Service;
    private final ResourceService resourceService;

    public void createDataTable(String resourceId) throws IOException {

        resourceService.existFindById(resourceId);

        InputStream file = s3Service.getFile(resourceId);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file))) {

            CSVReader csvReader = new CSVReader(reader);
            List<String[]> data = csvReader.readAll();

            if (!data.isEmpty()) {
                String[] columns = data.get(0); // 예시: CSV 파일의 첫 번째 행을 컬럼 이름으로 설정
                // 나머지 행들을 List<String[]>으로 변환
                List<String[]> rows = data.subList(1, data.size()-1);

                mongoTemplate.dropCollection(resourceId);
                mongoTemplate.createCollection(resourceId);

                for (String[] record : rows) {
                    // DynamicData 객체 생성
                    TableData tableData = new TableData();
                    // 동적으로 생성되는 컬럼들을 추가로 설정
                    for (int i = 0; i < columns.length; i++) {
                        tableData.setAttributes(columns[i], record[i]);
                    }
                    // MongoDB에 문서 추가
                    mongoTemplate.insert(tableData, resourceId);
                }

            }
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }

    public ResTableDto getTable(String resourceId){
        ResTableDto resTableDto = new ResTableDto();

        resourceService.existFindById(resourceId);
        List<TableData> tableDataList = mongoTemplate.find(new Query(), TableData.class,resourceId);
        if (tableDataList.size() != 0) {
            resTableDto.setCol(tableDataList.get(0).getAttributes().keySet().stream().toList());

            List<ResTableDto.RowDto> rowDtoList = new ArrayList<>();

            int num = 1;
            for (TableData data : tableDataList) {
                Iterator<Object> iterator = data.getAttributes().values().iterator();
                ResTableDto.RowDto rowDto = new ResTableDto.RowDto();
                while (iterator.hasNext()) {
                    Object value = iterator.next();
                    rowDto.add(value.toString());
                }
                rowDto.setId(Integer.toString(num));
                rowDtoList.add(rowDto);
                num++;
            }

            resTableDto.setRows(rowDtoList);
            return resTableDto;
        }
        throw new ResourceNotFoundException(ResponseMessage.NOT_EXIST_RESOURCE);

    }
}



