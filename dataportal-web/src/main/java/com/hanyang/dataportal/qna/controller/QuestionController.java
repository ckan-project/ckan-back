package com.hanyang.dataportal.qna.controller;

import com.hanyang.dataportal.core.dto.ApiResponse;
import com.hanyang.dataportal.core.exception.ResourceNotFoundException;
import com.hanyang.dataportal.qna.domain.Question;
import com.hanyang.dataportal.qna.dto.req.ReqQuestionDto;
import com.hanyang.dataportal.qna.dto.res.ResQuestionListDto;
import com.hanyang.dataportal.qna.service.QuestionService;
import com.hanyang.dataportal.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/question")
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<ReqQuestionDto>> createQuestion(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ReqQuestionDto reqQuestionDto) {
        //   User user = userDetails.getUsername(); ~ 로그인된 user 정보를 받아오는 부분은 나중에
        Question question = reqQuestionDto.toEntity();
       // question.setUser(user);
        return ResponseEntity.ok(ApiResponse.ok(questionService.createQuestion(question, userDetails.getUsername()));
    }

    @GetMapping("/list/{questionID}")
    public Question findDetailQuestion(Long questionId){
        return questionService.findDetailQuestion(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question 이 없음"));
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<Question>>> findAllQuestions() {
        List<Question> questions = questionService.findAllQuestion();
        return ResponseEntity.ok(ApiResponse.ok(questions));
    }

    @GetMapping("/list/myQuestion")
    public ResponseEntity<ApiResponse<List<>>>
}
