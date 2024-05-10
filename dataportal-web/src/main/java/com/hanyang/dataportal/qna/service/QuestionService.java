package com.hanyang.dataportal.qna.service;

import com.hanyang.dataportal.core.exception.ResourceNotFoundException;
import com.hanyang.dataportal.qna.domain.Question;
import com.hanyang.dataportal.qna.dto.req.ReqQuestionDto;
import com.hanyang.dataportal.qna.dto.res.ResQuestionListDto;
import com.hanyang.dataportal.qna.repository.QuestionRepository;
import com.hanyang.dataportal.user.domain.User;
import com.hanyang.dataportal.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final UserService userService;
    private final int PAGE_SIZE = 10;

    public Question save(ReqQuestionDto reqQuestionDto, String email) {
        User user = userService.findByEmail(email);
        Question question = reqQuestionDto.toEntity();
        question.setUser(user);
        return questionRepository.save(question);
    }

    public Question update(ReqQuestionDto reqQuestionDto, long questionId) {
        Question question = findById(questionId);
        question.update(reqQuestionDto);
        return question;
    }

    @Transactional(readOnly = true)
    public Question findById(Long questionId){
        return questionRepository.findById(questionId).orElseThrow(()-> new ResourceNotFoundException("해당 질문글은 존재하지 않습니다"));
    }

    public void delete(Long questionId) {
        questionRepository.delete(findById(questionId));
    }

    @Transactional(readOnly = true)
    public Page<Question> getQuestionList(int pageNum) {
        Pageable pageable = PageRequest.of(pageNum, PAGE_SIZE, Sort.by("date").descending());
        return questionRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Question> getMyQuestionList(String userName, int pageNum) {
        User user = userService.findByEmail(userName);
        Pageable pageable = PageRequest.of(pageNum, PAGE_SIZE, Sort.by("date").descending());
        return questionRepository.findByUser(user,pageable);
    }
}

