package com.hanyang.dataportal.qna.controller;

import com.hanyang.dataportal.core.response.ApiResponse;
import com.hanyang.dataportal.qna.domain.Question;
import com.hanyang.dataportal.qna.dto.req.ReqQuestionDto;
import com.hanyang.dataportal.qna.dto.res.ResQuestionDto;
import com.hanyang.dataportal.qna.dto.res.ResQuestionListDto;
import com.hanyang.dataportal.qna.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "질문(Question) API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questions")
public class QuestionController {
    private final QuestionService questionService;

    @Operation(summary = "질문글 작성")
    @PostMapping(value = "/")
    public ResponseEntity<ApiResponse<ResQuestionDto>> createQuestion(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ReqQuestionDto reqQuestionDto) {
        Question question = reqQuestionDto.toEntity();
        String username = userDetails.getUsername();
        questionService.save(question, username);
        ResQuestionDto resQuestionDto = ResQuestionDto.toQuestionDto((question));
        return ResponseEntity.ok(ApiResponse.ok(resQuestionDto));
    }

    @Operation(summary = "질문글 수정")
    @PutMapping(value = "/{questionId}")
    public ResponseEntity<ApiResponse<ResQuestionDto>> updateQuestion(@RequestBody ReqQuestionDto reqQuestionDto, @PathVariable long questionId) {
        Question question = reqQuestionDto.toUpdateEntity();
        questionService.updateQuestion(question, questionId);
        ResQuestionDto resQuestionDto = ResQuestionDto.toQuestionDto(question);
        return ResponseEntity.ok(ApiResponse.ok(resQuestionDto));
    }

    @Operation(summary = "질문글 삭제")
    @DeleteMapping(value = "/{questionId}")
    public ResponseEntity<ApiResponse<?>> deleteQuestion(@PathVariable long questionId, @AuthenticationPrincipal UserDetails userDetails) {
        questionService.deleteQuestion(questionId);
        return null;
    }
    @Operation(summary = "질문글 조회 (페이징)")
    //@GetMapping({"/list", "list?page={pageNum}&size={listSize}"})
    @GetMapping({"/list", "list"})
    public ResponseEntity<ApiResponse<Page<?>>> getQuestionList(@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                                                                @RequestParam(value = "size", defaultValue = "10") int listSize) throws Exception {
        Page<ResQuestionListDto> resQuestionListDto = questionService.getQuestionList(pageNum,listSize);
        return ResponseEntity.ok(ApiResponse.ok(resQuestionListDto));
   }



    @Operation(summary = "나의 질문글 내역리스트 (페이징)")
    @GetMapping("/list/my")
    public ResponseEntity<ApiResponse<List<ResQuestionListDto>>> getMyQuestionList(@AuthenticationPrincipal UserDetails userDetails, @RequestParam(value ="page", required = false, defaultValue = "1") int pageNum,
                                                                                   @RequestParam(value = "size", defaultValue = "10") int listSize)
   { String userName = userDetails.getUsername();
    List<ResQuestionListDto> resQuestionListDtos = questionService.getMyQuestionList(userName,pageNum,listSize);
    return ResponseEntity.ok(ApiResponse.ok(resQuestionListDtos));
   }

    @Operation(summary = "질문글 조회 (상세)")
    @GetMapping(value = "{questionId}")
    public ResponseEntity<ApiResponse<ResQuestionDto>> getQuestion(@PathVariable Long questionId) {
        Question question = questionService.getDetailQuestion(questionId);
        ResQuestionDto resQuestionDto = ResQuestionDto.toQuestionDto(question);
        return ResponseEntity.ok(ApiResponse.ok(resQuestionDto));
    }

    @Operation(summary = "나의 질문글 조회 (상세)")
    @GetMapping(value = "list/my/{questionId}")
    public ResponseEntity<ApiResponse<ResQuestionDto>> getQuestion(@PathVariable long questionId) {
        Question question = questionService.getDetailQuestion(questionId);
        ResQuestionDto resQuestionDto = ResQuestionDto.toQuestionDto(question);
        return ResponseEntity.ok(ApiResponse.ok(resQuestionDto));
    }

}