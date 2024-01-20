package com.hanyang.dataportal.exception.controller;


import com.hanyang.dataportal.user.dto.NoticeDTO;
import com.hanyang.dataportal.user.service.NoticeService;
import com.hanyang.dataportal.utill.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.hanyang.dataportal.utill.ApiResponse.successResponseNoContent;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor //@Autowired..?
public class NoticeController {

    private final NoticeService noticeService;
    @PostMapping ("/notice")
    public ApiResponse<?> save(NoticeDTO noticeDto) {
        noticeService.save(noticeDto);
        return successResponseNoContent();
    }

    @GetMapping("/notice")
    public ApiResponse<?> findById(noticeId) {
        noticeService.findById(noticeID);
        return ~?
    }
}


// 제네릭.. ->?? 리스트
