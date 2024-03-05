package com.hanyang.datastore.service;

import com.hanyang.datastore.controller.DataStoreController;
import com.hanyang.datastore.core.exception.LabelNotFoundException;
import com.hanyang.datastore.core.response.ResponseMessage;
import com.hanyang.datastore.domain.Content;
import com.hanyang.datastore.domain.MetaData;
import com.hanyang.datastore.domain.TableData;
import com.hanyang.datastore.dto.DatasetMetaDataDto;
import com.hanyang.datastore.dto.ResTableDto;
import com.hanyang.datastore.dto.ResTableLabelDto;
import com.hanyang.datastore.repository.ContentRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

@Service
@RequiredArgsConstructor
@Transactional
public class TableService {

    private final MongoTemplate mongoTemplate;
    private final S3Service s3Service;
    private final DataPortalService dataPortalService;
    private final ContentRepository contentRepository;

    /**
     * datasetId를 통해서
     * S3에서 데이터를 가져와서 MongoDB에 저장
     * datasetId에 해당하는 리소스가 S3에 없거나 파싱 불가능한 파일형태(pdf,docx)면 예외처리
     */
    public void createDataTable(String datasetId) throws IOException {

        DatasetMetaDataDto datasetMetaDataDto = dataPortalService.findDataset(datasetId);

        InputStream file = s3Service.getFile(datasetId);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file))) {

            CSVReader csvReader = new CSVReader(reader);
            List<String[]> data = csvReader.readAll();

            if (!data.isEmpty()) {
                String[] columns = data.get(0); // 예시: CSV 파일의 첫 번째 행을 컬럼 이름으로 설정
                // 나머지 행들을 List<String[]>으로 변환
                List<String[]> rows = data.subList(1, data.size());

                mongoTemplate.dropCollection(datasetId);
                mongoTemplate.createCollection(datasetId);
                MetaData metaData = new MetaData();
                metaData.setMetaData(datasetMetaDataDto);

                List<TableData> tableDataList = new ArrayList<>();
                for (String[] record : rows) {
                    // DynamicData 객체 생성
                    TableData tableData = new TableData();
                    // 동적으로 생성되는 컬럼들을 추가로 설정
                    for (int i = 0; i < columns.length; i++) {
                        tableData.setAttributes(columns[i], record[i]);
                    }
                    tableDataList.add(tableData);
                }
                mongoTemplate.insert(metaData, datasetId);
                mongoTemplate.insert(tableDataList,datasetId);

                //keyword search를 위한 Content
                Optional<Content> content = contentRepository.findByDatasetId(datasetId);
                content.ifPresent(contentRepository::delete);
                contentRepository.save(new Content(metaData.getDatasetId(), metaData.getTitle()+" "+metaData.getDescription()));
            }
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }

    public ResTableDto getTable(String resourceId){
        ResTableDto resTableDto = new ResTableDto();
        MetaData metaData = mongoTemplate.findOne(new Query(), MetaData.class,resourceId);
        List<TableData> tableDataList = mongoTemplate.find(new Query().skip(1), TableData.class,resourceId);

        assert metaData != null;
        resTableDto.setLabelName(metaData.getLabelName());
        resTableDto.setLabelList(metaData.getLabel());

        //컬럼개수
        int colCnt = tableDataList.get(0).getAttributes().size();
        //총 데이터 개수
        int dataCnt = tableDataList.size();
        List<String> dataNameList = tableDataList.get(0).getAttributes().keySet().stream().toList();
        resTableDto.setDataName(dataNameList);

        String[][] dataList = new String[colCnt-1][dataCnt];
        for (int i = 0; i < dataCnt; i++) {
            for (int j = 0; j < colCnt-1; j++) {
                String colName = dataNameList.get(j);
                dataList[j][i] = tableDataList.get(i).getAttributes().get(colName);
            }
        }
        resTableDto.setDataList(dataList);

        return resTableDto;

    }

    /**
     * labeling된 col과 다른 col을 통해 합계(값),평균으로 그룹핑
     * mongoDB에 Agrregation을 이용하여 파이프라인 구축
     */
    public ResTableLabelDto getAggregationLabel(String resourceId, String colName, DataStoreController.Type type) throws ParseException {
        String labelName = Objects.requireNonNull(mongoTemplate.findOne(new Query(), MetaData.class, resourceId)).getLabelName();
        if(labelName == null){
            throw new LabelNotFoundException(ResponseMessage.NOT_EXIST_LABEL);
        }

        Criteria criteria1 = Criteria.where("attributes." + labelName).exists(true); // 첫 번째 where 문
        Criteria criteria2 = Criteria.where("attributes." + colName).exists(true);

        //attributes가 있는 데이터 집계
        AggregationOperation matchOperation = Aggregation.match(criteria1.andOperator(criteria2));
        //문자열 double로 변환
        ProjectionOperation projectionOperation = project()
                .and("attributes." + labelName).as(labelName)
                .and(ConvertOperators.valueOf("attributes." + colName).convertToDouble()).as(colName);

        //label에 따른 그룹핑 및 합계
        AggregationOperation groupOperation;
        if(type == DataStoreController.Type.평균) {
            groupOperation = Aggregation.group(labelName).avg(colName).as(colName);
        }
        else {
            groupOperation = Aggregation.group(labelName).sum(colName).as(colName);
        }

        Aggregation aggregation = Aggregation.newAggregation(matchOperation,projectionOperation,groupOperation);

        AggregationResults<String> results = mongoTemplate.aggregate(aggregation, resourceId, String.class);

        ArrayList<String> labelList = new ArrayList<>();
        ArrayList<Double> dataList = new ArrayList<>();
        for (String result : results) {
            JSONParser jsonParser = new JSONParser();

            Object obj = jsonParser.parse(result);

            JSONObject jsonObj = (JSONObject) obj;
            // label
            String id = jsonObj.get("_id").toString();
            labelList.add(id);
            // col data
            Double value = Double.parseDouble(jsonObj.get(colName).toString());
            dataList.add(value);

        }
        ResTableLabelDto resTableLabelDto = new ResTableLabelDto();
        resTableLabelDto.setLabelName(labelName);
        resTableLabelDto.setLabelList(labelList);
        resTableLabelDto.setDataName(colName);
        resTableLabelDto.setDataList(dataList);

        return resTableLabelDto;
    }

    /**
     * dataset 라벨링
     */
    public void datasetLabeling(String resourceId,String labelName){

        List<TableData> tableDataList = mongoTemplate.find(new Query().skip(1), TableData.class,resourceId);
        Set<String> uniqueLabelData = new LinkedHashSet<>();

        for (TableData tableData : tableDataList) {
            String labelData = tableData.getAttributes().get(labelName);
            if (labelData != null) {
                uniqueLabelData.add(labelData);
            }
        }

        Update update = new Update();
        update.set("label",uniqueLabelData);
        update.set("labelName",labelName);

        mongoTemplate.updateFirst(new Query(), update, TableData.class, resourceId);

    }
}


