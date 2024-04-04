package com.hanyang.dataportal.qna.service;

import com.hanyang.dataportal.qna.repository.AnswerRepository;
import com.hanyang.dataportal.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
@Sql("/h2-truncate.sql")
@Transactional
public class AnswerServiceTest {
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private UserRepository userRepository;


    @Test
    @DisplayName("질문글을 등록한다")
    void save(){
    }

    @Test
    @DisplayName("답변을 수정한다")
    void update(){
    }

    @Test
    @DisplayName("답변을 조회(단건/상세)한다")
    void find_detail(){
    }

    @Test
    @DisplayName("답변을 조회(리스트)한다")
    void find_list(){
    }

    @Test
    @DisplayName("답변글을 삭제한다")
    void delete(){
    }

}
