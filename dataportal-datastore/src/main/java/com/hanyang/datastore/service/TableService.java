package com.hanyang.datastore.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanyang.datastore.domain.MetaData;
import com.hanyang.datastore.domain.TableData;
import com.hanyang.datastore.dto.DatasetMetaDataDto;
import com.hanyang.datastore.dto.ResTableLabelDto;
import com.hanyang.datastore.repository.MetaDataRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.unwind;

@Service
@RequiredArgsConstructor
@Transactional
public class TableService {
    private final S3Service s3Service;
    private final DataPortalService dataPortalService;
    private final MetaDataRepository metaDataRepository;
    private final MongoTemplate mongoTemplate;
    /**
     * datasetId를 통해서
     * S3에서 데이터를 가져와서 MongoDB에 저장
     * datasetId에 해당하는 리소스가 S3에 없거나 파싱 불가능한 파일형태(pdf,docx)면 예외처리
     */
    public void createDataTable(String datasetId) throws IOException {

        DatasetMetaDataDto datasetMetaDataDto = dataPortalService.findDataset(datasetId);

        InputStream file = s3Service.getFile(datasetId);

        Workbook workbook = new XSSFWorkbook(file);
        DataFormatter dataFormatter = new DataFormatter();
        Sheet sheet = workbook.getSheetAt(0);

        Optional<MetaData> findMeta = metaDataRepository.findById(datasetId);
        MetaData metaData;
        if(findMeta.isPresent()){
            metaData = findMeta.get();
            metaData.setDataListClean();
        }
        else{
            metaData = new MetaData(datasetMetaDataDto);
        }

        //col명 작성
        Row row1 = sheet.getRow(0);
        String[] columns = new String[row1.getPhysicalNumberOfCells()];
        Iterator<Cell> cellIterator = row1.iterator();
        for (int i=0;i<row1.getPhysicalNumberOfCells();i++) {
            Cell cell = cellIterator.next();
            String cellValue = dataFormatter.formatCellValue(cell);
            columns[i] = cellValue;
        }
        //data 추가
        List<TableData> tableDataList = new ArrayList<>();
        for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                TableData tableData =new TableData();
                LinkedHashMap<String,Object> map = new LinkedHashMap<>();
                Iterator<Cell> cellIterator2 = row.iterator();
                for (int i=0;i<row.getPhysicalNumberOfCells();i++) {
                    Cell cell = cellIterator2.next();
                    Double cellValue = null;
                    try {
                        cellValue = Double.parseDouble(dataFormatter.formatCellValue(cell));
                    } catch (NumberFormatException e) {
                        map.put(columns[i], dataFormatter.formatCellValue(cell));
                        continue;
                    }
                    map.put(columns[i],cellValue);
                }
                tableData.setData(map);
                tableDataList.add(tableData);
        }
        metaData.setDataList(tableDataList);
        metaDataRepository.save(metaData);
    }
    //TODO 테이블 데이터에 대한 반환
//    public ResTableDto getTable(String datasetId){
//        ResTableDto resTableDto = new ResTableDto();
//        MetaData metaData = tableDataRepository.findById(datasetId).orElseThrow(() -> new ResourceNotFoundException(NOT_EXIST_DATASET));
//
//        resTableDto.setLabelName(metaData.getLabelName());
//        resTableDto.setLabelList(metaData.getLabel());
//
//        //컬럼개수
//        int colCnt = metaData.getDataList().get(0).size();
//        //총 데이터 개수
//        int dataCnt = metaData.getDataList().size();
//        List<String> dataNameList = metaData.getDataList().get(0).keySet().stream().toList();
//        resTableDto.setDataName(dataNameList);
//
//        String[][] dataList = new String[colCnt-1][dataCnt];
//        for (int i = 0; i < dataCnt; i++) {
//            for (int j = 0; j < colCnt-1; j++) {
//                String colName = dataNameList.get(j);
//                dataList[j][i] = metaData.getDataList().get(i).get(colName);
//            }
//        }
//        resTableDto.setDataList(dataList);
//
//        return resTableDto;
//
//    }

    /**
     * labeling된 col과 다른 col을 통해 합계(값),평균으로 그룹핑
     * mongoDB에 Agrregation을 이용하여 파이프라인 구축
     */
    //TODO Xlabel이 없으면 기준값을 잘못 잡은것이기에 에러처리 해줘야함
    public ResTableLabelDto getAggregationLabel(String datasetId, String colName) throws JsonProcessingException {

        //Meta data만 조회
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(datasetId));
        query.fields().include("title").include("description").slice("dataList", 1);;

        MetaData meta = mongoTemplate.find(query, MetaData.class, "metaData").get(0);

        //aggregation 함수
        MatchOperation matchOperation = Aggregation.match(Criteria.where("_id").is(datasetId));
        UnwindOperation unwindOperation = unwind("dataList");
        GroupOperation groupOperation = Aggregation.group("data."+colName);

        //순서보장을 위해 LinkedHashSet으로
        Set<Map.Entry<String, Object>> entries = new LinkedHashSet<>(meta.getDataList().get(0).getData().entrySet());

        //dataList의 크기 지정 및 dataName 설정
        //동적으로 groupOperation할 col을 지정해줌
        List<List<Double>> dataList = new ArrayList<>();
        List<String> dataName = new ArrayList<>();
        for (Map.Entry<String, Object> map :entries) {
            if(!Objects.equals(map.getKey(), colName) && map.getValue() instanceof Double){
                groupOperation = groupOperation.sum("data."+map.getKey()).as(map.getKey());
                dataList.add(new ArrayList<>());
                dataName.add(map.getKey());
            }
        }

        //pipeLine 생성
        Aggregation aggregation = Aggregation.newAggregation(
                matchOperation,
                project().andInclude("dataList").andExclude("_id"),
                unwindOperation,
                project().and("dataList.data").as("data"),
                groupOperation
        );

        //pipeLine을 통해 나온 필요한 값들 추출
        List<String> result = mongoTemplate.aggregate(aggregation, "metaData", String.class).getMappedResults();
        List<String> xLabel = new ArrayList<>();

        for (String str:result) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(str);

            //xLabel 값 . 있으면 제거
            String id = jsonNode.path("_id").asText();
            if(!Objects.equals(id, "null")){
                xLabel.add(id.replaceAll("\\..*", ""));
            }

            int index = 0;
            for (Map.Entry<String, Object> map :entries) {
                if(!Objects.equals(map.getKey(), colName) && map.getValue() instanceof Double) {
                    double value = jsonNode.path(map.getKey()).asDouble();
                    dataList.get(index).add( Math.round(value * 100.0) / 100.0);
                    index++;
                }
            }

        }
        ResTableLabelDto resTableLabelDto = new ResTableLabelDto();
        resTableLabelDto.setX_axis_name(colName);
        resTableLabelDto.setX_label(xLabel);
        resTableLabelDto.setDataName(dataName);
        resTableLabelDto.setDataList(dataList);
        return resTableLabelDto;
    }

}


