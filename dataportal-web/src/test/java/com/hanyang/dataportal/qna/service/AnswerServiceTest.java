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

    @DisplayName("Answer Test 초기화")
    @BeforeEach
    public void init(){
        user = User.builder().email("admin@test.com").build();
        userRepository.save(user);


        //기존 코드 형태
//        Question question = Question.builder()() ... 에서 변경
        //class 다른 테스스케이스에서 단위로 접근이 안됨
        question = Question.builder()
                .id(1L)
                .title("Question Test Title")
                .content("Question Test Content")
                .build();
        question = questionRepository.save(question);

        answer = Answer.builder()
                .answerId(1L)
                .answerTitle("Answer Test Title")
                .answerContent("Answer Test Content")
                .question(question)
                .build();
        answer = answerRepository.save(answer);
    }


    @Test
    @DisplayName("질문글을 등록한다")
    void save(){
        //given

        //when
        Answer saveAnswer = answerService.save(answer, question.getId(),user.getEmail());
        //then
        // System.out.print(getAnswer);
        Assertions.assertThat(saveAnswer.getAnswerTitle()).isEqualTo("Answer Test Title");
        Assertions.assertThat(saveAnswer.getAnswerContent()).isEqualTo("Answer Test Content");
        /* 이런식으로 작성하는게 맞는것 같지는 않음. 그냥 확인 객체와 객체를 맞냐고 확인하는 것이어서..
        * 그러나 여기 어떻게 해야할지 아이디어가 떠오르지 않음.
        * 전체실행을 하면 @BeforeEach 어노테이션이 3번을 만들어서 수행하여서 그런것인지 기대값 1L이 아닌
        * 3L의 값이 나옴.*/
        // Assertions.assertThat(getAnswer.getQuestion().getId()).isEqualTo(getAnswer.getQuestion().getId());
        Assertions.assertThat(answer.getQuestion().getId()).isEqualTo(saveAnswer.getAnswerId());
        Assertions.assertThat(saveAnswer.getQuestion().getTitle()).isEqualTo("Question Test Title");
    }

    @Test
    @DisplayName("답변을 수정한다")
    void update(){
        //given
        Answer answer_update = Answer.builder().answerTitle("Update Answer Title").build();

        //when
        Answer get_answer = answerService.update(answer_update, 1L, user.getEmail());

        //then
        Assertions.assertThat(answer.getAnswerId()).isEqualTo(1L);
        Assertions.assertThat(get_answer.getAnswerTitle()).isEqualTo("Update Answer Title");
    }

    @Test
    @DisplayName("답변을 조회(단건/상세)한다")
    void find_detail(){
        //given

        //when
        Answer getDetailAnswer = answerService.getDetailAnswer((answer.getAnswerId()));

        //then
        Assertions.assertThat(answer.getAnswerId()).isEqualTo(getDetailAnswer.getAnswerId());
        Assertions.assertThat(getDetailAnswer.getAnswerTitle()).isEqualTo("Answer Test Title");
        Assertions.assertThat(getDetailAnswer.getAnswerContent()).isEqualTo("Answer Test Content");
        Assertions.assertThat(getDetailAnswer.getQuestion()).isEqualTo(question);

    }

    @Test
    @DisplayName("답변을 조회(리스트)한다")
    void find_list(int pageNum, int listSize){
        //given

        //when

        //then
    }

    @Test
    @DisplayName("답변글을 삭제한다")
    void delete(){
        //given

        //when
        answerService.

        //then

    }

}
