package com.hanyang.dataportal.qna.service;

import com.hanyang.dataportal.core.exception.ResourceNotFoundException;
import com.hanyang.dataportal.qna.domain.AnswerStatus;
import com.hanyang.dataportal.qna.domain.Question;
import com.hanyang.dataportal.qna.dto.req.ReqQuestionListDto;
import com.hanyang.dataportal.qna.dto.res.ResQuestionListDto;
import com.hanyang.dataportal.qna.dto.res.ResRtnQuestionListDto;
import com.hanyang.dataportal.qna.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service (value = "질문하기 서비스")
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question save(Question reqQuestionDto) {
        Question question = questionRepository.findByUser(reqQuestionDto.getUser());
        return questionRepository.save(question);
    }

    public void updateQuestion(Question reqUpdateQuestionDto, long questionId) {
        Optional<Question> updateQuestion = questionRepository.findById(questionId);
        if (updateQuestion.isPresent()) {
            questionRepository.save(reqUpdateQuestionDto);
        } else {
            throw new ResourceNotFoundException("수정할 질문글이 없습니다.");
        }
    }

    public Question getDetailQuestion(Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("조회된 질문글이 없습니다."));
    }

    public List<Question> getAllQuestion() {
        return questionRepository.findAll();
    }

    public List<Question> getAllMyQuestion(String loginKey) {
        return questionRepository.findById(loginKey);
    }

    public boolean deleteQuestion(Long questionId) {
        if (questionRepository.existsById(questionId)) {
            questionRepository.deleteById(questionId);
            return true;
        } else return false;
    }

    /* Completed 된 Question의 글 중에서 페이징 수를 가져오는 부분 ...  */
    public ResQuestionListDto getQuestionList(ReqQuestionListDto reqQuestionDto) {
        int page = reqQuestionDto.getPage();
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("date").descending());
        Page<Question> questionPage = questionRepository.findByAnswerStatus(AnswerStatus.Completed, pageRequest);
        return new ResQuestionListDto(
                questionPage.getTotalPages(),
                questionPage.getTotalElements()
           //      mapToResRtnQuestionListDto(questionPage.getContent())
        );
    }

    private ResRtnQuestionListDto mapToResRtnQuestionListDto(Question question) {
        return new ResRtnQuestionListDto(
                question.getQuestionId(),
                question.getContent(),
                question.getDate(),
                question.getView(),
                question.getUser().getName()
        );
    }
}

