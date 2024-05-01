package com.hanyang.dataportal.dataset.controller;

import com.hanyang.dataportal.core.response.ApiResponse;
import com.hanyang.dataportal.dataset.dto.res.ResOrganizationDto;
import com.hanyang.dataportal.dataset.service.OrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.hanyang.dataportal.core.response.ApiResponse.ok;

@Tag(name = "조직 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrganizationController {

    private final OrganizationService organizationService;

    @Operation(summary = "일치하는 키워드 별 조직 리스트 보기")
    @GetMapping("/organization")
    public ResponseEntity<ApiResponse<ResOrganizationDto>> findOrganization(@RequestParam String keyword){
        return ResponseEntity.ok(ok(new ResOrganizationDto(organizationService.findByKeyword(keyword))));
    }
}
