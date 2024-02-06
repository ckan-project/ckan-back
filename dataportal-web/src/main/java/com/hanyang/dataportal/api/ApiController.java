package com.hanyang.dataportal.api;

import com.hanyang.dataportal.core.response.ApiResponse;
import com.hanyang.dataportal.dataset.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ApiController {

    private final ResourceService resourceService;

    @GetMapping("/resource")
    public ResponseEntity<ApiResponse<?>> getFileExist(@RequestParam Long resourceId){
        return ResponseEntity.ok(ApiResponse.ok(resourceService.findById(resourceId)));
    }
}
