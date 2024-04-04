package com.hanyang.dataportal.qna.service;

import com.hanyang.dataportal.core.exception.ResourceNotFoundException;
import com.hanyang.dataportal.qna.domain.Answer;
import com.hanyang.dataportal.qna.domain.Question;
import com.hanyang.dataportal.qna.dto.res.ResAnswerListDto;
import com.hanyang.dataportal.qna.repository.AnswerRepository;
import com.hanyang.dataportal.qna.repository.QuestionRepository;
import com.hanyang.dataportal.user.domain.User;
import com.hanyang.dataportal.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {
    @Autowired
    private final QuestionRepository questionRepository;
    @Autowired
    private final AnswerRepository answerRepository;
    @Autowired
    private final UserService userService;

    public Answer save(Answer answer, Long questionId, String username) {
        User user = userService.findByEmail(username);
        Optional<Question> getQuestion = questionRepository.findById(questionId);
        answer.setAdmin(user);

       if(getQuestion.isPresent()) {
           // answer.setQuestion(getQuestion);
          return answerRepository.save(answer);
       } else
                throw new ResourceNotFoundException("답변할 질문글이 없음");
    }


    public Answer update(Answer answer, Long AnswerId, String username) {
        User user = userService.findByEmail(username);
        Optional<Answer> getAnswer = answerRepository.findById(AnswerId);

        answer.setAnswerId(AnswerId);
        if(getAnswer.isPresent()) {
            return answerRepository.save(answer);

        }else
            throw new ResourceNotFoundException("AnswerId로 조회된 글이 없음");

    }

    public Answer getDetailAnswer(Long answerId) {
        return answerRepository.findById(answerId)
                .orElseThrow(()-> new ResourceNotFoundException("답변글이 없음"));
    }

//    public List<Answer> getTodoAnswerList() {
//        return (List<Answer>) answerRepository.findByStatus("Waiting");
////        if (Answer.isEmpty()) {
////            throw new ResourceNotFoundException("답변해야 할 글이 없음");
//        }


    public List<ResAnswerListDto> getAnswerList(int pageNum, int listSize) {
        Pageable pageable = PageRequest.of(pageNum-1, listSize, Sort.by("date").descending());
        Page<Answer> answers = answerRepository.findAll(pageable);

        List<ResAnswerListDto> resAnswerListDtos = new ArrayList<>();
            /*
        each-for 문 사용법
        for (type 변수명: iterate) {
            body-of-loop }
       */
        for (Answer answer : answers) {
            ResAnswerListDto resQuestionListDto = ResAnswerListDto.toDto(answer);
            resAnswerListDtos.add(resQuestionListDto);
        }
        return resAnswerListDtos;
    }


}
