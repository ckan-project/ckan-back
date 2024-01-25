package com.hanyang.dataportal.dataset.controller;

import com.hanyang.dataportal.core.dto.ApiResponse;
import com.hanyang.dataportal.dataset.domain.Dataset;
import com.hanyang.dataportal.dataset.dto.req.ReqDatasetDto;
import com.hanyang.dataportal.dataset.dto.res.ResDatasetDetailDto;
import com.hanyang.dataportal.dataset.service.DatasetService;
import com.hanyang.dataportal.dataset.service.UpdateDatasetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.hanyang.dataportal.core.dto.ApiResponse.ok;

@Tag(name = "관리자 데이터셋 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AdminDatasetController {
    private final DatasetService datasetService;
    private final UpdateDatasetService updateDatasetService;
    @Operation(summary = "dataset 저장")
    @PostMapping("/dataset")
    public ResponseEntity<ApiResponse<ResDatasetDetailDto>> saveDataset(@RequestBody ReqDatasetDto reqDatasetDto){
        Dataset dataset = updateDatasetService.create(reqDatasetDto);
        return ResponseEntity.ok(ok(new ResDatasetDetailDto(dataset)));
    }
    @Operation(summary = "dataset 수정")
    @PutMapping("/dataset/{datasetId}")
    public ResponseEntity<ApiResponse<ResDatasetDetailDto>> updateDataset(@PathVariable Long datasetId, @RequestBody ReqDatasetDto reqDatasetDto){
        Dataset dataset = updateDatasetService.modify(datasetId, reqDatasetDto);
        return ResponseEntity.ok(ok(new ResDatasetDetailDto(dataset)));
    }
    @Operation(summary = "dataset 삭제")
    @DeleteMapping("/dataset/{datasetId}")
    public ResponseEntity<ApiResponse<?>> deleteDataset(@PathVariable Long datasetId){
        datasetService.deleteDataset(datasetId);
        return ResponseEntity.ok(ok(null));
    }
}
