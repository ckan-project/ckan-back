package com.hanyang.dataportal.qna.service;

import com.hanyang.dataportal.core.exception.ResourceNotFoundException;
import com.hanyang.dataportal.qna.domain.Answer;
import com.hanyang.dataportal.qna.domain.Question;
import com.hanyang.dataportal.qna.repository.AnswerRepository;
import com.hanyang.dataportal.qna.repository.QuestionRepository;
import com.hanyang.dataportal.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;

    public Answer saveAnswer(Answer reqAnswerEntity, Long questionId) {
//        Optional<Question> byId = questionRepository.findById(questionId);
//        reqAnswerEntity.setQuestion(byId.orElseThrow( () -> new ResourceNotFoundException("질문글이 없음. ")));

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("질문글이 없음"));
        reqAnswerEntity.setQuestion(question);
        return answerRepository.save(reqAnswerEntity);
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
}
