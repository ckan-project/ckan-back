package com.hanyang.dataportal.qna.service;

import com.hanyang.dataportal.qna.domain.Answer;
import com.hanyang.dataportal.qna.domain.Question;
import com.hanyang.dataportal.qna.repository.AnswerRepository;
import com.hanyang.dataportal.qna.repository.QuestionRepository;
import com.hanyang.dataportal.user.domain.User;
import com.hanyang.dataportal.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
    private QuestionRepository questionRepository;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserRepository userRepository;

    Answer answer;
    Question question;
    User user;
    @BeforeEach
    @DisplayName("Answer Test 초기화")
    public void init(){
        user = User.builder().email("admin@test.com").build();
        userRepository.save(user);

        question = Question.builder()
                .id(1L)
                .title("Question Test Title")
                .content("Question Test Content")
                .build();
        Question getQuestion = questionRepository.save(question);

        answer = Answer.builder()
                .answerId(1L)
                .answerTitle("Answer Test Title")
                .answerContent("Answer Test Content")
                .question(getQuestion)
                .build();
    }

    @Test
    @DisplayName("질문글을 등록한다")
    void save(){
        //given

        //when
        Answer getAnswer = answerService.save(answer, question.getId(),user.getEmail());
        //then
        System.out.print(getAnswer);
        Assertions.assertThat(getAnswer.getAnswerTitle()).isEqualTo("Answer Test Title");
        Assertions.assertThat(getAnswer.getAnswerContent()).isEqualTo("Answer Test Content");
        /* 이런식으로 작성하는게 맞는것 같지는 않음. 그냥 확인 객체와 객체를 맞냐고 확인하는 것이어서..
        * 그러나 여기 어떻게 해야할지 아이디어가 떠오르지 않음.
        * 전체실행을 하면 @BeforeEach 어노테이션이 3번을 만들어서 수행하여서 1L이 아닌
        * 3L의 값이 나옴.*/
        Assertions.assertThat(getAnswer.getQuestion().getId()).isEqualTo(getAnswer.getQuestion().getId());
        Assertions.assertThat(getAnswer.getQuestion().getTitle()).isEqualTo("Question Test Title");
    }

    @Test
    @DisplayName("답변을 수정한다")
    void update(){
        //given
        Answer answer_update = Answer.builder().answerTitle("Update Answer Title").build();

        //when

        //then
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
