package com.hanyang.dataportal.qna.service;

import com.hanyang.dataportal.core.exception.ResourceNotFoundException;
import com.hanyang.dataportal.qna.domain.Answer;
import com.hanyang.dataportal.qna.repository.AnswerRepository;
import com.hanyang.dataportal.qna.repository.QuestionRepository;
import com.hanyang.dataportal.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;


    public Answer saveAnswer(Answer reqAnswerDto, Long questionId) {
//        Optional<Question> getQuestion = Optional.ofNullable(questionRepository.findById(questionId)
//            .orElseThrow(() -> new ResourceNotFoundException("답변할 질문글이 없음")));
    reqAnswerDto.setQuestionId(questionId);
    return answerRepository.save(reqAnswerDto);
    }

    public Answer getDetailAnswer(Long answerId) {
        return answerRepository.findById(answerId)
                .orElseThrow(()-> new ResourceNotFoundException("답변글이 없음"));
    }

    public List<Answer> getTodoAnswerList() {
        return (List<Answer>) answerRepository.findByStatus("Waiting");
//        if (Answer.isEmpty()) {
//            throw new ResourceNotFoundException("답변해야 할 글이 없음");
        }

}
