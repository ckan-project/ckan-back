package com.hanyang.dataportal.dataset.controller;

import com.hanyang.dataportal.core.response.ApiResponse;
import com.hanyang.dataportal.dataset.domain.Dataset;
import com.hanyang.dataportal.dataset.dto.res.ResDatasetListDto;
import com.hanyang.dataportal.dataset.service.DatasetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.hanyang.dataportal.core.response.ApiResponse.ok;

@Tag(name = "My 페이지 API")
@RestController
@RequestMapping("/api/my")
@RequiredArgsConstructor
public class MyController {
    private final DatasetService datasetService;
    @Operation(summary = "My 다운로드 리스트")
    @GetMapping("/downloads")
    public ResponseEntity<ApiResponse<ResDatasetListDto>> getMyList(@AuthenticationPrincipal UserDetails userDetails) {
        Page<Dataset> dataList = datasetService.getMyDownloadsList(userDetails);
        return ResponseEntity.ok(ok(new ResDatasetListDto((dataList))));

    }

}
