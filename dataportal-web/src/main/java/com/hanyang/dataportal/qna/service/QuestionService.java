package com.hanyang.dataportal.qna.service;

import com.hanyang.dataportal.core.exception.ResourceNotFoundException;
import com.hanyang.dataportal.qna.domain.Question;
import com.hanyang.dataportal.qna.dto.res.ResQuestionListDto;
import com.hanyang.dataportal.qna.repository.QuestionRepository;
import com.hanyang.dataportal.user.domain.User;
import com.hanyang.dataportal.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service (value = "질문하기 서비스")
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserService userService;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question save(Question question, String username) {
        User user = userService.findByEmail(username);
        question.setUser(user);
        return questionRepository.save(question);
    }

    public Question updateQuestion(Question reqUpdateQuestionDto, long questionId) {
        Optional<Question> updateQuestion = questionRepository.findById(questionId);

        reqUpdateQuestionDto.setDate(updateQuestion.get().getDate());
        reqUpdateQuestionDto.setView(updateQuestion.get().getView());
        reqUpdateQuestionDto.setAnswerStatus(updateQuestion.get().getAnswerStatus());
        reqUpdateQuestionDto.setUser(updateQuestion.get().getUser());

        if (updateQuestion.isPresent()) {
            reqUpdateQuestionDto.setId(questionId);
            questionRepository.save(reqUpdateQuestionDto);
        } else {
            throw new ResourceNotFoundException("수정할 질문글이 없습니다.");
        }
        return reqUpdateQuestionDto;
    }

    public Question getDetailQuestion(Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("조회된 질문글이 없습니다."));
    }


    public void deleteQuestion(Long questionId) {
     if(questionRepository.existsById(questionId)){ // throw 로 확실히 예외처리를 하겠습니다.
         questionRepository.deleteById(questionId);
     } else {
         throw new ResourceNotFoundException("삭제할 글이 없음.");
     }
    }

    public Page<ResQuestionListDto> getQuestionList(int pageNum, int listSize) {
        Pageable pageable = PageRequest.of(pageNum-1, listSize, Sort.by("date").descending());
        Page<Question> questions = questionRepository.findAll(pageable);

        Page<ResQuestionListDto> resQuestionDtoList = questions.map(ResQuestionListDto::toDto);
        return resQuestionDtoList;
    }


    public List<ResQuestionListDto> getMyQuestionList(String userName, int pageNum, int listSize) {
        User user = userService.findByEmail(userName);
        Pageable pageable = PageRequest.of(pageNum-1, listSize, Sort.by("date").descending());
        Page<Question> questions = questionRepository.findByUser(user,pageable);
        questions.getContent();
        questions.getTotalElements();
        questions.getTotalPages();

        List<ResQuestionListDto> resQuestionList = new ArrayList<>();
        for(Question question : questions.getContent()) {
            resQuestionList.add(ResQuestionListDto.toDto(question));
        }
        return resQuestionList;
    }
}

