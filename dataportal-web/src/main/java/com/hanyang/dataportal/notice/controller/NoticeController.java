package com.hanyang.dataportal.notice.controller;

import com.hanyang.dataportal.core.dto.ApiResponse;
import com.hanyang.dataportal.notice.domain.Notice;
import com.hanyang.dataportal.notice.dto.req.ReqNoticeDto;
import com.hanyang.dataportal.notice.dto.res.ResNoticeDto;
import com.hanyang.dataportal.notice.dto.res.ResNoticesDto;
import com.hanyang.dataportal.notice.service.NoticeDetailService;
import com.hanyang.dataportal.notice.service.NoticeListService;
import com.hanyang.dataportal.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")

public class NoticeController {
    private final NoticeDetailService noticeDetailService;
    private final NoticeListService noticeListService;

    @PostMapping("/notice")
    public ResponseEntity<ApiResponse<ResNoticeDto>> createNotice(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ReqNoticeDto reqNoticeDto) {
        Notice notice = reqNoticeDto.toEntity();
        // String username = userDetails.getUsername();
        return ResponseEntity.ok(ApiResponse.ok(new ResNoticeDto(noticeDetailService.createNotice(notice, userDetails.getUsername()))));
    }

    @GetMapping("/notice/{noticeId}")
    public ResponseEntity<ApiResponse<ResNoticeDto>> findNotice(@PathVariable Long noticeId) {
        Notice notice = noticeDetailService.findNotice(noticeId);
        ResNoticeDto resNoticeDto = new ResNoticeDto(notice);
        return ResponseEntity.ok(ApiResponse.ok(resNoticeDto));
    }

    @GetMapping("/notices")
    public ResponseEntity<ApiResponse<List<ResNoticesDto>>> findAllNotice() {
        //List로 받아왔는데, 받는쪽이 List 타입이 아니.. 타입이 다르다.
        List<Notice> noticeList = noticeListService.findAllNotice();
        List<ResNoticesDto> noticesDtoList = new ArrayList<>();
        for (Notice notice : noticeList) {
            noticesDtoList.add(new ResNoticesDto(notice));
        }
        return ResponseEntity.ok(ApiResponse.ok(noticesDtoList));
    }

    @DeleteMapping("/{noticeId}")
    public ResponseEntity<ApiResponse<?>> deleteNotice(@PathVariable Long noticeId) {
        noticeDetailService.deleteNotice(noticeId);
        return ResponseEntity.ok(ApiResponse.ok(null));
    }

//    @PostMapping("/{noticeId}")
//    //@PutMapping("/{id})
//    public ResponseEntity<Notice> updateNotice(){
//        return ResponseEntity.ok(ApiResponse.ok(List.of(updateNotice())));
//    }

}

