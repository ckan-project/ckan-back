package com.hanyang.dataportal.qna.repository;

import com.hanyang.dataportal.qna.domain.Question;
import com.hanyang.dataportal.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {

    Page<Question> findAll(Pageable pageable);
    Page<Question> findByUser(User user, Pageable pageable);
    List<Question> findAllByUser(User user);



    // Question findByUser(User user);

//      Question findByUser(User user);


}
