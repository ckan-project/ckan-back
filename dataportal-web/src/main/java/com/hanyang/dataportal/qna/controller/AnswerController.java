package com.hanyang.dataportal.qna.controller;

import com.hanyang.dataportal.core.response.ApiResponse;
import com.hanyang.dataportal.qna.domain.Answer;
import com.hanyang.dataportal.qna.dto.req.ReqAnswerDto;
import com.hanyang.dataportal.qna.dto.res.ResAnswerDetailDto;
import com.hanyang.dataportal.qna.dto.res.ResAnswerDto;
import com.hanyang.dataportal.qna.dto.res.ResAnswerListDto;
import com.hanyang.dataportal.qna.service.AnswerService;
import com.hanyang.dataportal.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/answer")
public class AnswerController {
    private final AnswerService answerService;
    // 6. 답변하기
    @PostMapping(value = "/" , name = "답변하기")
    public ResponseEntity<ApiResponse<?>> saveAnswer(@RequestBody ReqAnswerDto reqAnswerDto, Long questionId, @AuthenticationPrincipal User user) {
     Answer answer = reqAnswerDto.toEntity();
     answerService.saveAnswer(answer,questionId);
     ResAnswerDto resAnswerDto = ResAnswerDto.toDto(answer);
     return ResponseEntity.ok(ApiResponse.ok(resAnswerDto));
    }

    // 7. 답변 상세보기
    @GetMapping(value = "/{answerId}", name ="답변 상세보기")
    public ResponseEntity<ApiResponse<?>> getDetailAnswer(@PathVariable Long answerId) {
        Answer answer = answerService.getDetailAnswer(answerId);
        ResAnswerDetailDto resAnswerDto = ResAnswerDetailDto.toDetailDto(answer);
        return ResponseEntity.ok(ApiResponse.ok(resAnswerDto));
    }

    // 8. 답변할 질문 리스트 보기
    // AnswerService에서 AnswerStatus가 waiting 상태인 것을 골라 answerRepository에서 조회하여 반환하면 될 것으로 생각..
    @GetMapping(value = "/list", name = "답변할 질문 리스트보기")
    public ResponseEntity<ApiResponse<?>> getTodoAnswerList() {
        List<Answer> answerList = answerService.getTodoAnswerList();
        List<ResAnswerListDto> resAnswerListDto = new ArrayList<>();

        for(Answer answer : answerList) {
            resAnswerListDto.add(ResAnswerListDto.toListDto(answer));
        }

        return ResponseEntity.ok(ApiResponse.ok(resAnswerListDto));
    }



}
