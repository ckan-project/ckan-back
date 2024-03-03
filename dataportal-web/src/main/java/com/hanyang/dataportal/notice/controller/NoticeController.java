package com.hanyang.dataportal.notice.controller;

import com.hanyang.dataportal.core.response.ApiResponse;
import com.hanyang.dataportal.notice.domain.Notice;
import com.hanyang.dataportal.notice.dto.req.ReqNoticeDto;
import com.hanyang.dataportal.notice.dto.res.ResNoticeDto;
import com.hanyang.dataportal.notice.dto.res.ResNoticeListDto;
import com.hanyang.dataportal.notice.service.NoticeDetailService;
import com.hanyang.dataportal.notice.service.NoticeListService;
import com.hanyang.dataportal.qna.dto.res.ResQuestionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.hanyang.dataportal.notice.dto.res.ResNoticeDto.toNoticeDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notices")
public class NoticeController {
    private final NoticeDetailService noticeDetailService;
    private final NoticeListService noticeListService;

    // 1. 공지사항 작성
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ResQuestionDto>> createNotice(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ReqNoticeDto reqNoticeDto ) {
        Notice notice = reqNoticeDto.toEntity();
        String username = userDetails.getUsername();
        noticeDetailService.createNotice(notice, username);
                // notice를 dto 타입으로 변환

        ResNoticeDto resNoticeDto = ResNoticeDto.toNoticeDto(notice);
        return ResponseEntity.ok(ApiResponse.ok(resNoticeDto));
    }

    // 2-1. 공지사항 조회 (단건조회)
    @GetMapping("/{noticeId}")
    public ResponseEntity<ApiResponse<ResQuestionDto>> findNotice(@PathVariable Long noticeId) {
       Notice notice = noticeDetailService.findNotice(noticeId);
            ResNoticeDto resNoticeDto = ResNoticeDto.toNoticeDetailDto(notice);
            return ResponseEntity.ok(ApiResponse.ok(List.of(resNoticeDto)));
    }

    // 2-2. 공지사항 조회 (리스트 조회)
    @GetMapping("/list")
    public ResponseEntity<ApiResponse<ResQuestionDto>> findListNotice() {

        List<Notice> noticeList = noticeListService.findAllNotice();
        List<ResNoticeListDto> resNoticeListDtoList = new ArrayList<>();

        for (Notice notice : noticeList) {
            resNoticeListDtoList.add(ResNoticeListDto.toResNoticeListDto(notice));
        }
        // return resNoticeListDtoList;


        return ResponseEntity.ok(ApiResponse.ok(resNoticeListDtoList));
    }

    // 3. 공지사항 수정 (update)
    @PostMapping("/update/{noticeId}")
    public ResponseEntity<ApiResponse<ResQuestionDto>> updateNotice(@PathVariable Long noticeId, @RequestBody ReqNoticeDto reqNoticeDto) {
        Notice notice = reqNoticeDto.toUpdateEntity();
        noticeDetailService.updateNotice(notice, noticeId);
        ResNoticeDto resNoticeDto = toNoticeDto(notice);
        return ResponseEntity.ok(ApiResponse.ok(resNoticeDto));
    }
    // 4. 공지사항 삭제
    @DeleteMapping("/delete/{noticeId}")
    public ResponseEntity<ApiResponse<?>> deleteNotice(@PathVariable Long noticeId) {
        noticeDetailService.deleteNotice(noticeId);
        return null;
        }
    }

    // 공지사항 ~
    // 1. 공지사항 작성
    // 2. 공지사항 조회 (단건조회 / 리스트조회)
    // 3. 공지사항 수정 (update)
    // 4. 공지사항 삭제


//    @DeleteMapping("/{noticeId}")
//
//    @PostMapping("/{noticeId}")
//    //@PutMapping("/{id})
//    public ResponseEntity<Notice> updateNotice(){
//
//        return ResponseEntity.ok(ApiResponse.ok(List.of(updateNotice())));
//    }

