package com.hanyang.dataportal.faq.controller;

import com.hanyang.dataportal.core.response.ApiResponse;
import com.hanyang.dataportal.faq.domain.Faq;
import com.hanyang.dataportal.faq.dto.ReqFaqDto;
import com.hanyang.dataportal.faq.dto.ResFaqDto;
import com.hanyang.dataportal.faq.dto.ResFaqListDto;
import com.hanyang.dataportal.faq.service.FaqService;
import com.hanyang.dataportal.qna.domain.QuestionCategory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "FAQ API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FaqController {
    private final FaqService faqService;
    @Operation(summary = "FAQ 등록")
    @PostMapping("/faq")
    public ResponseEntity<ApiResponse<ResFaqDto>> createQuestion(@RequestBody ReqFaqDto reqFaqDto) {
        Faq faq = faqService.create(reqFaqDto);
        return ResponseEntity.ok(ApiResponse.ok(new ResFaqDto(faq)));
    }
    @Operation(summary = "FAQ 수정")
    @PutMapping("/faq/{faqId}")
    public ResponseEntity<ApiResponse<ResFaqDto>> updateFaq(@PathVariable Long faqId, @RequestBody ReqFaqDto reqFaqDto) {
        Faq faq = faqService.update(faqId,reqFaqDto);
        return ResponseEntity.ok(ApiResponse.ok(new ResFaqDto(faq)));
    }
    @Operation(summary = "FAQ 조회 (페이징)")
    @GetMapping("/faqs")
    public ResponseEntity<ApiResponse<ResFaqListDto>> getFaqList(@RequestParam Integer page) {
        Page<Faq> faqListDto = faqService.getFaqList(page);
        return ResponseEntity.ok(ApiResponse.ok(new ResFaqListDto(faqListDto)));
    }
    @Operation(summary = "FAQ 카테고리별 조회")
    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<ResFaqListDto>> getFaqsByCategory(@PathVariable QuestionCategory questionCategory) {
        Page<Faq> faqCategoryList = faqService.getFaqsByCategory(questionCategory);
        // List<Faq> faqs = faqCategoryList.getContent();
        return ResponseEntity.ok(ApiResponse.ok(new ResFaqListDto(faqCategoryList)));

    }
    @Operation(summary = "FAQ 삭제")
    @DeleteMapping("/faq/{faqId}")
    public ResponseEntity<ApiResponse<?>> deleteFaq(@PathVariable Long faqId){
        faqService.delete(faqId);
        return ResponseEntity.ok(ApiResponse.ok(null));
    }
}
