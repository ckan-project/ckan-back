package com.hanyang.dataportal.qna.repository;

import com.hanyang.dataportal.qna.domain.Question;
import com.hanyang.dataportal.qna.dto.res.ResMyQuestionListDto;
import com.hanyang.dataportal.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {

    static Optional<Question> findById(Long questionId);


    List<ResMyQuestionListDto> findAllById(User user);
}
