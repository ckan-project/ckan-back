package com.hanyang.dataportal.dataset.controller;

import com.hanyang.dataportal.dataset.dto.req.ReqResourceDto;
import com.hanyang.dataportal.dataset.service.ResourceService;
import com.hanyang.dataportal.exception.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.hanyang.dataportal.exception.ApiResponse.successResponseNoContent;

@Tag(name = "리소스 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ResourceController {

    private final ResourceService resourceService;

    @Operation(summary = "resource 저장")
    @PostMapping ("/dataset/{datasetId}/resource")
    public ApiResponse<?> createResource(@PathVariable Long datasetId, @RequestBody ReqResourceDto reqResourceDto){

        resourceService.createResource(datasetId,reqResourceDto);

        return successResponseNoContent();
    }
}
