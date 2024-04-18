package com.hanyang.dataportal.qna.controller;

import com.hanyang.dataportal.core.response.ApiResponse;
import com.hanyang.dataportal.qna.domain.Question;
import com.hanyang.dataportal.qna.dto.req.ReqQuestionDto;
import com.hanyang.dataportal.qna.dto.res.ResQuestionDto;
import com.hanyang.dataportal.qna.dto.res.ResQuestionListDto;
import com.hanyang.dataportal.qna.service.QuestionService;
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

    //    1. 질문하기 (생성, 수정, 삭제)
    @PostMapping(value = "/", name = "질문하기 API (생성) ")
    public ResponseEntity<ApiResponse<ResQuestionDto>> createQuestion(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ReqQuestionDto reqQuestionDto) {
        Question question = reqQuestionDto.toEntity();
        String username = userDetails.getUsername();
        questionService.save(question, username);
        ResQuestionDto resQuestionDto = ResQuestionDto.toQuestionDto((question));
        return ResponseEntity.ok(ApiResponse.ok(resQuestionDto));
    }

    @PostMapping(value = "/{questionId}", name = "질문하기 API (수정)")
    public ResponseEntity<ApiResponse<ResQuestionDto>> updateQuestion(@RequestBody ReqQuestionDto reqQuestionDto, @PathVariable long questionId) {
        Question question = reqQuestionDto.toUpdateEntity();
        questionService.updateQuestion(question, questionId);
        ResQuestionDto resQuestionDto = ResQuestionDto.toQuestionDto(question);
        return ResponseEntity.ok(ApiResponse.ok(resQuestionDto));
    }

    @DeleteMapping(value = "/{questionId}", name = "질문하기 API (삭제)")
    public ResponseEntity<ApiResponse<?>> deleteQuestion(@PathVariable long questionId, @AuthenticationPrincipal UserDetails userDetails) {
        questionService.deleteQuestion(questionId);
        return null;
    }

    //    2. 질문 내역 리스트보기 (페이징, size는 10으로 고정~ 총 페이지:총데이터 갯수:질문리스트(QuestionId,title,date,view,username)) 응답
    //restful? ~ 클라이언트로부터 요청을 받는데, (몇페이지에 있는 게시글을볼 것인지?)
    // 페이징 조건 ~ 최신순, 인기순, 댓글순
    // 게시판 리스트 ~ 게시글번호, 작성일자, 제목, 작성자, 답변여부 , 조회수
    @GetMapping({"/list", "list?page={pageNum}&size={listSize}"})
    public ResponseEntity<ApiResponse<Page<?>>> getQuestionList(@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                                                                @RequestParam(value = "size", defaultValue = "10") int listSize) throws Exception {

        Page<ResQuestionListDto> resQuestionListDto = questionService.getQuestionList(pageNum,listSize);
        return ResponseEntity.ok(ApiResponse.ok(resQuestionListDto));
   }

    //    3. 나의 질문 내역 리스트보기 _로그인 value를 전달하여 findbyuser...로 긁어오기?


    //전체페이지 수와 total element 수?
   @GetMapping({ "/list/my", "list/my"})
    public ResponseEntity<ApiResponse<List<ResQuestionListDto>>> getMyQuestionList(@AuthenticationPrincipal UserDetails userDetails, @RequestParam(value ="page", required = false, defaultValue = "1") int pageNum,
                                                                               @RequestParam(value = "size", defaultValue = "10") int listSize)
   { String userName = userDetails.getUsername();
    List<ResQuestionListDto> resQuestionListDtos = questionService.getMyQuestionList(userName,pageNum,listSize);
    return ResponseEntity.ok(ApiResponse.ok(resQuestionListDtos));
   }

    // 4. 질문 내역 상세보기
    @GetMapping(value = "{questionId}", name = "질문 내역 상세보기 ")
    public ResponseEntity<ApiResponse<ResQuestionDto>> getQuestion(@PathVariable Long questionId) {
        Question question = questionService.getDetailQuestion(questionId);
        ResQuestionDto resQuestionDto = ResQuestionDto.toQuestionDto(question);
        return ResponseEntity.ok(ApiResponse.ok(resQuestionDto));
    }

    // 5. 나의 질문 내역 상세보기
    @GetMapping(value = "myQuestionList/{questionId}", name = "나의 질문 내역 상세보기 ")
    public ResponseEntity<ApiResponse<ResQuestionDto>> getQuestion(@PathVariable long questionId) {
        Question question = questionService.getDetailQuestion(questionId);
        ResQuestionDto resQuestionDto = ResQuestionDto.toQuestionDto(question);
        return ResponseEntity.ok(ApiResponse.ok(resQuestionDto));
    }


    // 6. 답변하기 -> AnswerController

    // 7. 답변 상세보기 -> AnswerController

    // 8. 답변할 질문 리스트 보기 -> AnswerController


}