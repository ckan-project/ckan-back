    package com.hanyang.dataportal.dataset.controller;

    import com.hanyang.dataportal.core.response.ApiResponse;
    import com.hanyang.dataportal.dataset.domain.Dataset;
    import com.hanyang.dataportal.dataset.dto.res.ResDatasetListDto;
    import com.hanyang.dataportal.dataset.service.ResourceService;
    import io.swagger.v3.oas.annotations.Operation;
    import io.swagger.v3.oas.annotations.tags.Tag;
    import lombok.RequiredArgsConstructor;
    import org.springframework.data.domain.Page;
    import org.springframework.http.MediaType;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.core.annotation.AuthenticationPrincipal;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;

    @Tag(name = "파일데이터 API")
    @RestController
    @RequestMapping("/api/dataset")
    @RequiredArgsConstructor
    public class ResourceController {

        private final ResourceService resourceService;

        @Operation(summary = "파일 데이터 저장 및 수정")
        @PutMapping(value = "/{datasetId}/resource", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<?> saveResource(@PathVariable Long datasetId, @RequestPart(value = "file") MultipartFile multipartFile) {
            resourceService.save(datasetId, multipartFile);
            return ResponseEntity.ok(ApiResponse.ok(null));
        }

        @Operation(summary = "유저 리소스 다운로드")
        @PostMapping(value = "/{datasetId}/resource/download")
        public ResponseEntity<?> downloadResource(@AuthenticationPrincipal UserDetails userDetail, @PathVariable Long datasetId) {
            resourceService.download(userDetail, datasetId);
            return ResponseEntity.ok(ApiResponse.ok(null));
        }



        /* 인기데이터를 리스트 가져오기 현재 UI구성이 6개 이므로 6개만 반환됨 */
        @Operation(summary = "인기 데이터 리스트가져오기")
        @GetMapping(value = "/resource/popular-data")
        public ResponseEntity<ApiResponse<ResDatasetListDto>> popularData() {

            Page<Dataset> datasets = resourceService.popularData();
            return ResponseEntity.ok(ApiResponse.ok(new ResDatasetListDto(datasets)));
        }


        /* 신규데이터를 리스트 가져오기 현재 UI구성이 6개 이므로 6개만 반환됨 */
        @Operation(summary = "신규 데이터 리스트가져오기")
        @GetMapping(value = "/resource/new-data")
        public ResponseEntity<ApiResponse<ResDatasetListDto>> newData() {
            Page<Dataset> datasets = resourceService.newData();
            return ResponseEntity.ok(ApiResponse.ok(new ResDatasetListDto(datasets)));
        }

    }



