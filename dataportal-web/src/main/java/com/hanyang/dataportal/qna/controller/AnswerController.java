package com.hanyang.dataportal.qna.controller;

import com.hanyang.dataportal.core.response.ApiResponse;
import com.hanyang.dataportal.qna.domain.Answer;
import com.hanyang.dataportal.qna.dto.req.ReqAnswerDto;
import com.hanyang.dataportal.qna.dto.res.ResAnswerDetailDto;
import com.hanyang.dataportal.qna.dto.res.ResAnswerDto;
import com.hanyang.dataportal.qna.dto.res.ResAnswerListDto;
import com.hanyang.dataportal.qna.service.AnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Tag(name = "답변(Answer) API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/answer")
public class AnswerController {
    private final AnswerService answerService;

    @Operation(summary = "질문에 대한 답변생성")
    @PostMapping(value = "/")
    public ResponseEntity<ApiResponse<?>> saveAnswer(@RequestBody ReqAnswerDto reqAnswerDto, Long questionId, @AuthenticationPrincipal UserDetails userDetails) {
        Answer answer = reqAnswerDto.toEntity();
        String username = userDetails.getUsername();
        answerService.save(answer, questionId, username);
     ResAnswerDto resAnswerDto = ResAnswerDto.toDto(answer);
     return ResponseEntity.ok(ApiResponse.ok(resAnswerDto));
    }

    @Operation(summary = "질문에 대한 답변수정")
    @PutMapping(value = "/{answerId}")
    public ResponseEntity<ApiResponse<?>> update(@RequestParam ReqAnswerDto reqAnswerDto, @PathVariable Long answerId, @AuthenticationPrincipal UserDetails userDetails) {

        Answer answer = reqAnswerDto.toEntity();
        String username = userDetails.getUsername();

        Answer res_answer = answerService.update(answer, answerId, username);
        ResAnswerDto resAnswerDto = ResAnswerDto.toDto(res_answer);
        return ResponseEntity.ok(ApiResponse.ok(resAnswerDto));
    }

    @Operation(summary = "질문에 대한 답변삭제")
    @DeleteMapping(value = "/{answerId}" )
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable long answerId, @AuthenticationPrincipal UserDetails userDetails) {
        String userName = userDetails.getUsername();
        answerService.delete(answerId, userName);
        return null;

    }

    @Operation(summary = "질문에 대한 답변상세 보기")
    @GetMapping(value = "/{answerId}")
    public ResponseEntity<ApiResponse<?>> getDetailAnswer(@PathVariable Long answerId) {
        Answer answer = answerService.getDetailAnswer(answerId);
        ResAnswerDetailDto resAnswerDto = ResAnswerDetailDto.toDetailDto(answer);
        return ResponseEntity.ok(ApiResponse.ok(resAnswerDto));
    }

    @Operation(summary = "질문글 리스트조회")
    @GetMapping(value = "/list")
    public ResponseEntity<ApiResponse<?>> getTodoAnswerList(@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                                                            @RequestParam(value =  "size", defaultValue = "10")int listSize)  {
        Page<ResAnswerListDto> answerList = answerService.getAnswerList(pageNum, listSize);
        return ResponseEntity.ok(ApiResponse.ok(answerList));
    }
}
