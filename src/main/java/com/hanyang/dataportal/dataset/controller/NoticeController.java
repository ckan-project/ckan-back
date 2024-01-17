package com.hanyang.dataportal.dataset.controller;

import com.hanyang.dataportal.dataset.domain.Notice;
import com.hanyang.dataportal.dataset.dto.req.ReqNoticeDto;
import com.hanyang.dataportal.dataset.dto.res.ResNoticeDto;
import com.hanyang.dataportal.dataset.service.NoticeService;
import com.hanyang.dataportal.utill.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "공지 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NoticeController {
    private final NoticeService noticeService;

    @Operation(summary = "notice 리스트 보기")
    @GetMapping("/notices")
    public ApiResponse<List<ResNoticeDto>> getNoticeList() {
        List<ResNoticeDto> noticeList = noticeService.getNotices()
                .stream()
                .map(ResNoticeDto::new)
                .toList();
        return ApiResponse.successResponse(noticeList);
    }

    @Operation(summary = "notice 찾기")
    @GetMapping("/notice")
    public ApiResponse<?> getNotice(@RequestParam Long id) {
        try {
            Notice notice = noticeService.findNotice(id);
            return ApiResponse.successResponse(new ResNoticeDto(notice));
        } catch (EntityNotFoundException e) {
            return ApiResponse.errorResponse(404, e.getMessage());
        }

    }

    @Operation(summary = "notice 작성")
    @PostMapping("/notice/{userId}")
    public ApiResponse<?> postNotice(@PathVariable Long userId, @RequestBody ReqNoticeDto reqNoticeDto) {
        try {
            Notice notice = noticeService.postNotice(reqNoticeDto, userId);
            return ApiResponse.successResponse(new ResNoticeDto(notice));
        } catch (EntityNotFoundException e) {
            return ApiResponse.errorResponse(404, e.getMessage());
        }
    }
}
