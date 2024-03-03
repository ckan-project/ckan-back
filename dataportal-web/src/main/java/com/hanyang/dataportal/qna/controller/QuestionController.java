package com.hanyang.dataportal.qna.controller;

import com.hanyang.dataportal.core.response.ApiResponse;
import com.hanyang.dataportal.qna.domain.Question;
import com.hanyang.dataportal.qna.dto.req.ReqQuestionDto;
import com.hanyang.dataportal.qna.dto.res.ResQuestionDto;
import com.hanyang.dataportal.qna.dto.res.ResQuestionListDto;
import com.hanyang.dataportal.qna.dto.res.ResQuestionMyListDto;
import com.hanyang.dataportal.qna.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questions")
public class QuestionController {
    private final QuestionService questionService;

    //    1. 질문하기 (생성, 수정, 삭제)
    @PostMapping(value = "/create", name = "질문하기 API (생성) ")
    public ResponseEntity<ApiResponse<ResQuestionDto>> createQuestion(@RequestBody ReqQuestionDto reqQuestionDto) {
        Question question = reqQuestionDto.toEntity();
        questionService.save(question);
        return ResponseEntity.ok(ApiResponse.ok(reqQuestionDto));
    }

    @PostMapping(value = "/update/{questionId}", name = "질문하기 API (수정)")
    public ResponseEntity<ApiResponse<ResQuestionDto>> updateQuestion(@PathVariable long questionId, @RequestBody ReqQuestionDto reqQuestionDto) {
        Question question = reqQuestionDto.toUpdateEntity();
        questionService.updateQuestion(question, questionId);
        ResQuestionDto resQuestionDto = ResQuestionDto.toQuestionDto(question);
        return ResponseEntity.ok(ApiResponse.ok(resQuestionDto));
    }

    @DeleteMapping(value = "{questionId}", name = "질문하기 API (삭제)")
    public ResponseEntity<ApiResponse<?>> deleteQuestion(@PathVariable long questionId) {
        questionService.deleteQuestion(questionId);
        return null;
    }

    //    2. 질문 내역 리스트보기 (페이징, size는 10으로 고정~ 총 페이지:총데이터 갯수:질문리스트(QuestionId,title,date,view,username)) 응답
    @GetMapping("/questionList")
    public ResponseEntity<ApiResponse<ResQuestionDto>> getQuestionList() {
        List<Question> reqQuestionListDto = questionService.getAllQuestion();
        List<ResQuestionDto> resQuestionDtoList = new ArrayList<>();
        for (Question question : reqQuestionListDto) {
            resQuestionDtoList.add(ResQuestionListDto.toResQuestionListDto(question));
        }
        /* 페이징 처리 부분은 미구현 상태임 */
        return ResponseEntity.ok(ApiResponse.ok(resQuestionDtoList));
    }

    //    3. 나의 질문 내역 리스트보기 _로그인 value를 전달하여 findbyuser...로 긁어오기?
    @GetMapping(value = "/myQuestionList")
    public ResponseEntity<ApiResponse<ResQuestionDto>> getMyQuestionList(@AuthenticationPrincipal String loginKey) {
        List<Question> reqQuestionMyListDto = questionService.getAllMyQuestion(loginKey);
        List<ResQuestionDto> resQuestionMyDtoList = new ArrayList<>();
        for (Question question : reqQuestionMyListDto) {
            resQuestionMyDtoList.add(ResQuestionMyListDto.toResQuestionMyListDto(question));
        }
        /* 페이징 처리 부분은 미구현 상태임 */
        return ResponseEntity.ok(ApiResponse.ok(resQuestionMyDtoList));
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