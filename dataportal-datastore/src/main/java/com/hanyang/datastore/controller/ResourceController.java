package com.hanyang.datastore.controller;

import com.hanyang.datastore.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ResourceController {

    private final ResourceService resourceService;

    //s3에 저장
    @PostMapping("/dataset/{datasetId}/resource")
    public ResponseEntity<Void> puts3(@PathVariable Long datasetId,@RequestParam MultipartFile file) throws IOException {

        resourceService.uploadResource(datasetId,file);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //s3에서 꺼내와서 data->table화 시킴
    @PostMapping("/dataset/{datasetId}/resource/{resourceId}/table")
    public ResponseEntity<Void> datastore(@PathVariable String datasetId,@PathVariable String resourceId){

        //TODO 테이블 생성(이름은 랜덤으로) portal 컬럼 이랑 연동
        resourceService.createDataTable(datasetId,resourceId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
