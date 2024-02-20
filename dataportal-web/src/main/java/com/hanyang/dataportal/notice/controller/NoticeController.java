package com.hanyang.dataportal.notice.controller;

import com.hanyang.dataportal.core.dto.ApiResponse;
import com.hanyang.dataportal.notice.domain.Notice;
import com.hanyang.dataportal.notice.dto.req.ReqNoticeDto;
import com.hanyang.dataportal.notice.dto.res.ResNoticeDto;
import com.hanyang.dataportal.notice.service.NoticeDetailService;
import com.hanyang.dataportal.user.domain.Scrap;
import com.hanyang.dataportal.user.domain.User;
import com.hanyang.dataportal.user.dto.res.ResScrapDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notices")

public class NoticeController {
    private final NoticeDetailService noticeDetailService;
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<List<ReqNoticeDto>>> createNotice(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ReqNoticeDto reqNoticeDto ) {
        Notice notice = reqNoticeDto.toEntity();
        String username = userDetails.getUsername();
        noticeDetailService.createNotice(notice, username);
        return ResponseEntity.ok(ApiResponse.ok(List.of(reqNoticeDto)));
    }

    @GetMapping("/{noticeId}")
    public ResponseEntity<ApiResponse<List<ResNoticeDto>>> findNotice(@PathVariable Long noticeId) {
       Notice notice = noticeDetailService.findNotice(noticeId);
        if (notice != null) {
            ResNoticeDto resNoticeDto = ResNoticeDto.fromEntity(notice);
            return ResponseEntity.ok(ApiResponse.ok(List.of(resNoticeDto)));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Notice not found"));
        }
    }

//    @DeleteMapping("/{noticeId}")
//
//    @PostMapping("/{noticeId}")
//    //@PutMapping("/{id})
//    public ResponseEntity<Notice> updateNotice(){
//
//        return ResponseEntity.ok(ApiResponse.ok(List.of(updateNotice())));
//    }

}