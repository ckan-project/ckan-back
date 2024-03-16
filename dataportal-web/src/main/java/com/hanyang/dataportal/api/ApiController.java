package com.hanyang.dataportal.api;

import com.hanyang.dataportal.api.dto.ResTitleDescDto;
import com.hanyang.dataportal.core.response.ApiResponse;
import com.hanyang.dataportal.dataset.domain.Dataset;
import com.hanyang.dataportal.dataset.service.DatasetService;
import com.hanyang.dataportal.dataset.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ApiController {

    private final DatasetService datasetService;

    @GetMapping("/dataset")
    public ResponseEntity<ResTitleDescDto> getDatasetMetaData(@RequestParam Long datasetId){
        Dataset dataset = datasetService.findById(datasetId);
        return ResponseEntity.ok(new ResTitleDescDto(dataset.getDatasetId(),dataset.getTitle(), dataset.getDescription()));
    }
}
