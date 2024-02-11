package com.hanyang.dataportal.dataset.controller;

import com.hanyang.dataportal.core.global.reponse.ApiResponse;
import com.hanyang.dataportal.dataset.domain.Dataset;
import com.hanyang.dataportal.dataset.dto.res.ResDatasetDetailDto;
import com.hanyang.dataportal.dataset.dto.res.ResDatasetListDto;
import com.hanyang.dataportal.dataset.service.ReadDatasetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.hanyang.dataportal.core.global.reponse.ApiResponse.ok;

@Tag(name = "데이터셋 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DatasetController {
    private final ReadDatasetService readDatasetService;

    @Operation(summary = "dataset 리스트 보기")
    @GetMapping("/datasets")
    public ResponseEntity<ApiResponse<ResDatasetListDto>> getDatasetList(@RequestParam(defaultValue = "입학") String theme,
                                                                               @RequestParam(defaultValue = "입학처") String organization,
                                                                               @RequestParam(defaultValue = "") String keyword,
                                                                               @RequestParam(defaultValue = "view") String sort,
                                                                               @RequestParam(defaultValue = "0") int page){
        ResDatasetListDto datasets = readDatasetService.getDatasetList(theme,organization,keyword,sort,page);
        return ResponseEntity.ok(ok(datasets));
    }


    @Operation(summary = "dataset 상세 보기")
    @GetMapping("/dataset/{datasetId}")
    public ResponseEntity<ApiResponse<ResDatasetDetailDto>> getDataset(@PathVariable Long datasetId){
        Dataset dataset = readDatasetService.getDatasetDetail(datasetId);
        return ResponseEntity.ok(ok(new ResDatasetDetailDto(dataset)));
    }

}
