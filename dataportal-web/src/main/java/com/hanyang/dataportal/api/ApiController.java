package com.hanyang.dataportal.api;

import com.hanyang.dataportal.core.global.reponse.ApiResponse;
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
        resourceService.findById(resourceId);
        return ResponseEntity.ok(ApiResponse.ok(null));
    }
}
