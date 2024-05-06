//package com.hanyang.dataportal.faq.controller;
//
//import com.hanyang.dataportal.core.response.ApiResponse;
//import com.hanyang.dataportal.faq.domain.Faq;
//import com.hanyang.dataportal.faq.dto.ResFaqListDto;
//import com.hanyang.dataportal.faq.service.FaqService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@Tag(name = "FAQ API")
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api")
//public class FaqController {
//    private final FaqService faqService;
//
//    @Operation(summary = "FAQ 조회 (페이징)")
//    @GetMapping("/faqs")
//    public ResponseEntity<ApiResponse<ResFaqListDto>> getFaqList(@RequestParam Integer page) throws Exception{
//        // Page<Faq> faqs = faqService.getFaqList(page);
//        // Page<ResFaqListDto> dtoPage = faqs.map(faq -> new ResFaqListDto(faq)); // Faq 객체를 ResFaqListDto로 변환
//        return ResponseEntity.ok(ApiResponse.ok(new ResFaqListDto(faqs)));
//    }
//
//
////    public ResponseEntity<ApiResponse<ResNoticeListDto>> findListNotice(@RequestParam Integer page) {
////        Page<Notice> noticeList = noticeService.getNoticeList(page);
////        return ResponseEntity.ok(ApiResponse.ok(new ResNoticeListDto(noticeList)));
////    }
//
//
//
//}
