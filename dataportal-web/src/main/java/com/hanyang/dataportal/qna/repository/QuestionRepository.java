package com.hanyang.dataportal.qna.repository;

import com.hanyang.dataportal.qna.domain.AnswerStatus;
import com.hanyang.dataportal.qna.domain.Question;
import com.hanyang.dataportal.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {

    Page<Question> findByAnswerStatus(AnswerStatus answerStatus, PageRequest pageable);
    Question findByUser(User user);

    List<Question> findById(String loginKey);
}
