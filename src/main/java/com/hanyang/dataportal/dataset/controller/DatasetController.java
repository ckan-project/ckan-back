package com.hanyang.dataportal.dataset.controller;

import com.hanyang.dataportal.dataset.domain.Dataset;
import com.hanyang.dataportal.dataset.dto.req.ReqDatasetDto;
import com.hanyang.dataportal.dataset.dto.res.ResDatasetDetailDto;
import com.hanyang.dataportal.dataset.dto.res.ResDatasetDto;
import com.hanyang.dataportal.dataset.dto.res.ResDatasetIdDto;
import com.hanyang.dataportal.dataset.service.DatasetService;
import com.hanyang.dataportal.exception.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.hanyang.dataportal.exception.ApiResponse.successResponse;
import static com.hanyang.dataportal.exception.ApiResponse.successResponseNoContent;

@Tag(name = "데이터셋 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DatasetController {

    private final DatasetService datasetService;


    @Operation(summary = "dataset 리스트 보기")
    @GetMapping("/datasets")
    public ApiResponse<List<ResDatasetDto>> getDatasetList(){

        List<ResDatasetDto> datasets = datasetService.getDatasets().stream().map(ResDatasetDto::new).toList();

        return successResponse(datasets);
    }

    @Operation(summary = "dataset 저장")
    @PostMapping("/dataset")
    public ApiResponse<ResDatasetIdDto> postDataset(@RequestBody ReqDatasetDto reqDatasetDto){

        Dataset dataset = datasetService.postDataset(reqDatasetDto);

        return successResponse(new ResDatasetIdDto(dataset.getDatasetId()));
    }

    @Operation(summary = "dataset 보기")
    @GetMapping("/dataset/{datasetId}")
    public ApiResponse<ResDatasetDetailDto> getDataset(@PathVariable Long datasetId){

        Dataset dataset = datasetService.getDatasetDetail(datasetId);

        return successResponse(new ResDatasetDetailDto(dataset));
    }


    @Operation(summary = "dataset 수정")
    @PutMapping("/dataset/{datasetId}")
    public ApiResponse<?>updateDataset(@PathVariable Long datasetId,@RequestBody ReqDatasetDto reqDatasetDto){

        datasetService.updateDataset(datasetId,reqDatasetDto);

        return successResponseNoContent();
    }

    @Operation(summary = "dataset 삭제")
    @DeleteMapping("/dataset/{datasetId}")
    public ApiResponse<?> deleteDataset(@PathVariable Long datasetId){

        datasetService.deleteDataset(datasetId);

        return successResponseNoContent();
    }






}
