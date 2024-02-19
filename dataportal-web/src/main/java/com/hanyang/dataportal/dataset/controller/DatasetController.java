package com.hanyang.dataportal.dataset.controller;

import com.hanyang.dataportal.core.response.ApiResponse;
import com.hanyang.dataportal.dataset.domain.Dataset;
import com.hanyang.dataportal.dataset.domain.Organization;
import com.hanyang.dataportal.dataset.domain.Theme;
import com.hanyang.dataportal.dataset.dto.DatasetSearchCond;
import com.hanyang.dataportal.dataset.dto.res.ResDatasetDetailDto;
import com.hanyang.dataportal.dataset.dto.res.ResDatasetListDto;
import com.hanyang.dataportal.dataset.service.ReadDatasetService;
import com.hanyang.dataportal.dataset.utill.DatasetSort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.hanyang.dataportal.core.response.ApiResponse.ok;

@Tag(name = "데이터셋 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DatasetController {
    private final ReadDatasetService readDatasetService;

    @Operation(summary = "dataset 리스트 보기")
    @GetMapping("/datasets")
    public ResponseEntity<ApiResponse<?>> getDatasetList(@RequestParam(required = false) String keyword,
                                                         @RequestParam(required = false) Organization organization,
                                                         @RequestParam(required = false) List<Theme> themeList,
                                                         @RequestParam(defaultValue = "최신") DatasetSort sort,
                                                         @RequestParam(defaultValue = "0") int page){
        DatasetSearchCond datasetSearchCond = new DatasetSearchCond(keyword,organization,themeList,sort,page);
        Page<Dataset> datasetList = readDatasetService.getDatasetList(datasetSearchCond);
        return ResponseEntity.ok(ok(new ResDatasetListDto(datasetList)));
    }


    @Operation(summary = "dataset 상세 보기")
    @GetMapping("/dataset/{datasetId}")
    public ResponseEntity<ApiResponse<ResDatasetDetailDto>> getDataset(@PathVariable Long datasetId){
        Dataset dataset = readDatasetService.getDatasetDetail(datasetId);
        return ResponseEntity.ok(ok(new ResDatasetDetailDto(dataset)));
    }

}
