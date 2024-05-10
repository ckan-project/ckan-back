package com.hanyang.dataportal.qna.service;

import com.hanyang.dataportal.core.exception.ResourceNotFoundException;
import com.hanyang.dataportal.qna.domain.Answer;
import com.hanyang.dataportal.qna.domain.Question;
import com.hanyang.dataportal.qna.dto.req.ReqAnswerDto;
import com.hanyang.dataportal.qna.dto.res.ResAnswerListDto;
import com.hanyang.dataportal.qna.repository.AnswerRepository;
import com.hanyang.dataportal.qna.repository.QuestionRepository;
import com.hanyang.dataportal.user.domain.User;
import com.hanyang.dataportal.user.repository.UserRepository;
import com.hanyang.dataportal.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AnswerService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final UserService userService;
    final int PAGE_SIZE = 10;

    public Answer save(ReqAnswerDto reqAnswerDto, Long questionId, String username) {
        User user = userService.findByEmail(username);
        Question question = questionRepository.findById(questionId).orElseThrow(()-> new ResourceNotFoundException("해당 질문글은 존재하지 않습니다"));
        Answer answer = reqAnswerDto.toEntity();
        answer.setAdmin(user);
        answer.setQuestion(question);
        return answer;
    }

    public Answer update(ReqAnswerDto reqAnswerDto, Long answerId) {
        Answer answer = findById(answerId);
        answer.update(reqAnswerDto);
        return answer;
    }

    public void delete(Long answerId) {
        answerRepository.delete(findById(answerId));
    }

    public Answer findById(Long answerId) {
        return answerRepository.findById(answerId).orElseThrow(()-> new ResourceNotFoundException("답변글이 없음"));
    }


    public Page<Answer> getAnswerList(int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return answerRepository.findAll(pageable);
    }
}
