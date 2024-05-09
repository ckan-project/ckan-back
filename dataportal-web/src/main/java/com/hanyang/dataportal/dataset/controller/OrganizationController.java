package com.hanyang.dataportal.dataset.controller;

import com.hanyang.dataportal.core.response.ApiResponse;
import com.hanyang.dataportal.dataset.dto.res.ResDatasetTitleDto;
import com.hanyang.dataportal.dataset.dto.res.ResOrganizationDto;
import com.hanyang.dataportal.dataset.dto.res.ResThemeListDto;
import com.hanyang.dataportal.dataset.service.DatasetService;
import com.hanyang.dataportal.dataset.service.OrganizationService;
import com.hanyang.dataportal.dataset.service.ThemeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.hanyang.dataportal.core.response.ApiResponse.ok;

@Tag(name = "리스트 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrganizationController {

    private final OrganizationService organizationService;
    private final DatasetService datasetService;
    private final ThemeService themeService;

    @Operation(summary = "일치하는 키워드 별 조직 리스트 보기")
    @GetMapping("/organizations")
    public ResponseEntity<ApiResponse<ResOrganizationDto>> findOrganization(@RequestParam(required = false,defaultValue = "") String keyword){
        return ResponseEntity.ok(ok(new ResOrganizationDto(organizationService.findByKeyword(keyword))));
    }
    @Operation(summary = "일치하는 키워드 별 데이터셋 제목 리스트 보기")
    @GetMapping("/dataset/title")
    public ResponseEntity<ApiResponse<ResDatasetTitleDto>> getDatasetListByKeyword(@RequestParam String keyword){
        List<String> titleList = datasetService.getByKeyword(keyword);
        return ResponseEntity.ok(ok(new ResDatasetTitleDto(titleList)));
    }

    @Operation(summary = "주제 리스트 보기")
    @GetMapping("/themes")
    public ResponseEntity<ApiResponse<ResThemeListDto>> getTheme(){
        return ResponseEntity.ok(ok(new ResThemeListDto(themeService.getAllTheme())));
    }
}
