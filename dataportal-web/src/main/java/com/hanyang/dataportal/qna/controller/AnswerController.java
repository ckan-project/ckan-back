package com.hanyang.dataportal.qna.controller;

import com.hanyang.dataportal.core.response.ApiResponse;
import com.hanyang.dataportal.qna.domain.Answer;
import com.hanyang.dataportal.qna.dto.req.ReqAnswerDto;
import com.hanyang.dataportal.qna.dto.res.ResAnswerDetailDto;
import com.hanyang.dataportal.qna.dto.res.ResAnswerDto;
import com.hanyang.dataportal.qna.dto.res.ResAnswerListDto;
import com.hanyang.dataportal.qna.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/answer")
public class AnswerController {
    private final AnswerService answerService;
    // 6-1. 답변하기 (생성, 수정, 삭제)
    @PostMapping(value = "/" , name = "답변하기")
    public ResponseEntity<ApiResponse<?>> saveAnswer(@RequestBody ReqAnswerDto reqAnswerDto, Long questionId, @AuthenticationPrincipal UserDetails userDetails) {
        Answer answer = reqAnswerDto.toEntity();
        String username = userDetails.getUsername();
        answerService.save(answer, questionId, username);
     ResAnswerDto resAnswerDto = ResAnswerDto.toDto(answer);
     return ResponseEntity.ok(ApiResponse.ok(resAnswerDto));
    }

    //6-2. 답변하기 (수정)
    @PostMapping(value = "/{answerId}", name = "답변수정")
    public ResponseEntity<ApiResponse<?>> update(@RequestParam ReqAnswerDto reqAnswerDto, @PathVariable Long answerId, @AuthenticationPrincipal UserDetails userDetails) {

        Answer answer = reqAnswerDto.toEntity();
        String username = userDetails.getUsername();

        Answer res_answer = answerService.update(answer, answerId, username);
        ResAnswerDto resAnswerDto = ResAnswerDto.toDto(res_answer);
        return ResponseEntity.ok(ApiResponse.ok(resAnswerDto));
    }

    @GetMapping(value = "/{answerId}" , name= "답변수정")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable long answerId, @AuthenticationPrincipal UserDetails userDetails) {
        answerService.delete(answerId,userDetails);
        return null;

    }

    // 7. 답변 상세보기
    @GetMapping(value = "/{answerId}", name ="답변 상세보기")
    public ResponseEntity<ApiResponse<?>> getDetailAnswer(@PathVariable Long answerId) {
        Answer answer = answerService.getDetailAnswer(answerId);
        ResAnswerDetailDto resAnswerDto = ResAnswerDetailDto.toDetailDto(answer);
        return ResponseEntity.ok(ApiResponse.ok(resAnswerDto));
    }

    // 8. 질문 리스트 보기
    // AnswerService에서 AnswerStatus가 waiting 상태인 것을 골라 answerRepository에서 조회하여 반환하면 될 것으로 생각..
    @GetMapping(value = "/list", name = "질문 리스트보기")
    public ResponseEntity<ApiResponse<?>> getTodoAnswerList(@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                                                            @RequestParam(value =  "size", defaultValue = "10")int listSize) throws Exception {

        Page<ResAnswerListDto> answerList = answerService.getAnswerList(pageNum, listSize);
//        List<ResAnswerDto> resAnswerListDto = new ArrayList<>();
//
//        for(Answer answer : answerList) {
//            resAnswerListDto.add(ResAnswerDto.toDto(answer));
//        }
        return ResponseEntity.ok(ApiResponse.ok(answerList));
    }
}
