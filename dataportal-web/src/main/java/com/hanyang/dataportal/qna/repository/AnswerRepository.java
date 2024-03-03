package com.hanyang.dataportal.qna.repository;

import com.hanyang.dataportal.qna.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer,Long> {


    List<Answer> findByStatus(String waiting);
}
