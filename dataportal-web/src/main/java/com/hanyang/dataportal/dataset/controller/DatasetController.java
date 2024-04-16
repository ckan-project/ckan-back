package com.hanyang.dataportal.dataset.controller;

import com.hanyang.dataportal.core.response.ApiResponse;
import com.hanyang.dataportal.dataset.domain.Dataset;
import com.hanyang.dataportal.dataset.dto.req.ReqDataSearchDto;
import com.hanyang.dataportal.dataset.dto.res.ResDatasetDetailDto;
import com.hanyang.dataportal.dataset.dto.res.ResDatasetListDto;
import com.hanyang.dataportal.dataset.dto.res.ResDatasetTitleDto;
import com.hanyang.dataportal.dataset.service.DatasetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.hanyang.dataportal.core.response.ApiResponse.ok;

@Tag(name = "데이터셋 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DatasetController {
    private final DatasetService datasetService;

    @Operation(summary = "dataset 리스트 보기")
    @GetMapping("/datasets")
    public ResponseEntity<ApiResponse<ResDatasetListDto>> getDatasetList(ReqDataSearchDto datasearch){
        Page<Dataset> datasetList = datasetService.getDatasetList(datasearch);
        return ResponseEntity.ok(ok(new ResDatasetListDto(datasetList)));
    }


    @Operation(summary = "dataset 상세 보기")
    @GetMapping("/dataset/{datasetId}")
    public ResponseEntity<ApiResponse<ResDatasetDetailDto>> getDataset(@PathVariable Long datasetId){
        return ResponseEntity.ok(ok(datasetService.getDatasetDetail(datasetId)));
    }

    @Operation(summary = "일치하는 키워드 별 데이터셋 제목 리스트 보기")
    @GetMapping("/datasets/{keyword}")
    public ResponseEntity<ApiResponse<ResDatasetTitleDto>> getDatasetListByKeyword(@PathVariable String keyword){
        List<String> titleList = datasetService.getByKeyword(keyword);
        return ResponseEntity.ok(ok(new ResDatasetTitleDto(titleList)));
    }

}
