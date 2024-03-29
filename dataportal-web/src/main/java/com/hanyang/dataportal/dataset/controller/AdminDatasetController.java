package com.hanyang.dataportal.dataset.controller;

import com.hanyang.dataportal.core.response.ApiResponse;
import com.hanyang.dataportal.dataset.domain.Dataset;
import com.hanyang.dataportal.dataset.dto.req.ReqDatasetDto;
import com.hanyang.dataportal.dataset.dto.res.ResDatasetDetailDto;
import com.hanyang.dataportal.dataset.service.DatasetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.hanyang.dataportal.core.response.ApiResponse.ok;

@Tag(name = "관리자 데이터셋 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AdminDatasetController {
    private final DatasetService datasetService;
    @Operation(summary = "dataset 저장")
    @PostMapping("/dataset")
    public ResponseEntity<ApiResponse<ResDatasetDetailDto>> saveDataset(@Validated @RequestBody ReqDatasetDto reqDatasetDto){
        Dataset dataset = datasetService.create(reqDatasetDto);
        return ResponseEntity.ok(ok(new ResDatasetDetailDto(dataset)));
    }
    @Operation(summary = "dataset 수정")
    @PutMapping("/dataset/{datasetId}")
    public ResponseEntity<ApiResponse<ResDatasetDetailDto>> updateDataset(@PathVariable Long datasetId,@Validated @RequestBody ReqDatasetDto reqDatasetDto){
        Dataset dataset = datasetService.modify(datasetId, reqDatasetDto);
        return ResponseEntity.ok(ok(new ResDatasetDetailDto(dataset)));
    }
    @Operation(summary = "dataset 삭제")
    @DeleteMapping("/dataset/{datasetId}")
    public ResponseEntity<ApiResponse<?>> deleteDataset(@PathVariable Long datasetId){
        datasetService.delete(datasetId);
        return ResponseEntity.ok(ok(null));
    }
}
