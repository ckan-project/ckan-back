package com.hanyang.datastore.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hanyang.datastore.core.response.ApiResponse;
import com.hanyang.datastore.core.response.ResponseMessage;
import com.hanyang.datastore.dto.ResTableLabelDto;
import com.hanyang.datastore.service.TableService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "데이터 스토어 API")
public class DataStoreController {

    private final TableService tableService;

    //s3에서 꺼내와서 data->table화 시킴
    @Operation(summary = "파일 데이터 테이블화")
    @PostMapping("/dataset/{resourceId}/resource/table")
    public ResponseEntity<ApiResponse<?>> datastore(@PathVariable String resourceId) throws IOException {
        tableService.createDataTable(resourceId);
        return ResponseEntity.ok(ApiResponse.ok(null));
    }

    /**
     * 테이블 데이터에 대한 반환 API
     */
//    @Operation(summary = "테이블 데이터 가져오기")
//    @GetMapping("/dataset/{resourceId}/resource/table")
//    public ResponseEntity<ApiResponse<ResTableDto>> getTable(@PathVariable String resourceId){
//        return ResponseEntity.ok(ApiResponse.ok(tableService.getTable(resourceId)));
//    }

    /**
     *
     * @param resourceId
     * @param colName colName은 독립변수
     * @return
     */
    @Operation(summary = "파일 데이터 시각화 차트 데이터")
    @GetMapping("/dataset/{resourceId}/table/group/label")
    public ResponseEntity<ApiResponse<ResTableLabelDto>> groupLabel(@PathVariable String resourceId, @RequestParam String colName) throws JsonProcessingException {
        return ResponseEntity.ok(ApiResponse.ok(tableService.getAggregationLabel(resourceId,colName)));
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(EntityNotFoundException ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.fail(ResponseMessage.NOT_EXIST_RESOURCE));
    }


}
