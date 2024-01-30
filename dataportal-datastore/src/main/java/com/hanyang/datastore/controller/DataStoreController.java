package com.hanyang.datastore.controller;

import com.hanyang.datastore.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class DataStoreController {

    private final TableService tableService;

    //s3에서 꺼내와서 data->table화 시킴
    @PostMapping("/dataset/{datasetId}/resource/table")
    public ResponseEntity<Void> datastore(@PathVariable String datasetId) throws IOException {

        //TODO 테이블 생성(이름은 랜덤으로) portal 컬럼 이랑 연동
        tableService.createDataTable(datasetId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
