package com.hanyang.dataportal.dataset.controller;

import com.hanyang.dataportal.core.response.ApiResponse;
import com.hanyang.dataportal.dataset.dto.res.ResResourceDto;
import com.hanyang.dataportal.dataset.service.ResourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "파일데이터 API")
@RestController
@RequestMapping("/api/dataset")
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;

    @Operation(summary = "파일 데이터 저장 및 수정")
    @PutMapping(value = "/{datasetId}/resource", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveResource(@PathVariable Long datasetId, @RequestPart(value = "file") MultipartFile multipartFile) throws IOException {
        return ResponseEntity.ok(ApiResponse.ok(new ResResourceDto(resourceService.save(datasetId, multipartFile))));
    }

}
