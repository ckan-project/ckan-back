package com.hanyang.dataportal.qna.service;


import com.hanyang.dataportal.qna.domain.Question;
import com.hanyang.dataportal.qna.dto.res.ResMyQuestionListDto;
import com.hanyang.dataportal.qna.repository.QuestionRepository;
import com.hanyang.dataportal.user.domain.User;
import com.hanyang.dataportal.user.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.hanyang.dataportal.qna.repository.QuestionRepository.findById;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UserService userService;

    public QuestionService(QuestionRepository questionRepository, UserService userService) {
        this.questionRepository = questionRepository;
        this.userService = userService;
    }


    public Optional<Question> findDetailQuestion(Long questionId){
        return findById(questionId);
    }
    public List<Question> findAllQuestion() {
        return questionRepository.findAll();
    }

    public List<ResMyQuestionListDto> findMyQuestion(User user){
        return questionRepository.findAllById(user);
    }

    public Question createQuestion(Question reqQuestionDto, String email) {
        User user = userService.findByEmail(email);
        Question question = new Question();
        question.setQuestionId(reqQuestionDto.getQuestionId());
        question.setTitle(reqQuestionDto.getTitle());
        question.setContent(reqQuestionDto.getContent());
        question.setAnswerStatus(reqQuestionDto.getAnswerStatus());
        question.setView(0);
        question.setDate(LocalDate.now());
        question.setUser(user);

        return questionRepository.save(question);
    }


}
