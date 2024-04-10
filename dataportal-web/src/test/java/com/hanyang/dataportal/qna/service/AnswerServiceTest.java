package com.hanyang.dataportal.qna.service;

import com.hanyang.dataportal.qna.domain.Answer;
import com.hanyang.dataportal.qna.domain.Question;
import com.hanyang.dataportal.qna.dto.res.ResAnswerListDto;
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
import org.springframework.data.domain.Page;
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
        User user_1 = User.builder().email("1@test.com").build();
        User user_2 = User.builder().email("2@test.com").build();
        User user_3 = User.builder().email("3@test.com").build();
        User user_4 = User.builder().email("4@test.com").build();
        User user_5 = User.builder().email("5@test.com").build();
        userRepository.save(user_1);
        userRepository.save(user_2);
        userRepository.save(user_3);
        userRepository.save(user_4);
        userRepository.save(user_5);

        User user_6 = User.builder().email("1@test.com").build();
        User user_7 = User.builder().email("2@test.com").build();
        User user_8 = User.builder().email("3@test.com").build();
        User user_9 = User.builder().email("4@test.com").build();
        User user_10 = User.builder().email("5@test.com").build();
        userRepository.save(user_6);
        userRepository.save(user_7);
        userRepository.save(user_8);
        userRepository.save(user_9);
        userRepository.save(user_10);


        Question question_1 = Question.builder().title("1").content("1 번째글 ").build();
        Question question_2 = Question.builder().title("2").content("2 번째글 ").build();
        Question question_3 = Question.builder().title("3").content("3 번째글 ").build();
        Question question_4 = Question.builder().title("4").content("4 번째글 ").build();
        Question question_5 = Question.builder().title("5").content("5 번째글 ").build();
        Question getquestion_1 = questionService.save(question_1,user_1.getEmail());
        Question getquestion_2 = questionService.save(question_2,user_2.getEmail());
        Question getquestion_3 = questionService.save(question_3,user_3.getEmail());
        Question getquestion_4 = questionService.save(question_4,user_1.getEmail());
        Question getquestion_5 = questionService.save(question_5,user_1.getEmail());



        Answer answer_1 = Answer.builder().answerTitle("1").answerContent("1 번째글 ").build();
        Answer answer_2 = Answer.builder().answerTitle("2").answerContent("2 번째글 ").build();
        Answer answer_3 = Answer.builder().answerTitle("3").answerContent("3 번째글 ").build();
        Answer answer_4 = Answer.builder().answerTitle("4").answerContent("4 번째글 ").build();
        Answer answer_5 = Answer.builder().answerTitle("5").answerContent("5 번째글 ").build();

        answerService.save(answer_1, getquestion_1.getId(), user_1.getEmail());
        answerService.save(answer_2, getquestion_2.getId(), user_2.getEmail());
        answerService.save(answer_3, getquestion_3.getId(), user_3.getEmail());
        answerService.save(answer_4, getquestion_4.getId(), user_1.getEmail());
        answerService.save(answer_5, getquestion_5.getId(), user_1.getEmail());

        //when
        Page<ResAnswerListDto> getAnswerPage =  answerService.getAnswerList(2,3);

        //then
        Assertions.assertThat(getAnswerPage.getTotalPages()).isEqualTo(2);

    }

    @Test
    @DisplayName("답변글을 삭제한다")
    void delete(){
        //given

        //when
        answerService.delete(1L, "admin@test.com");
        //then
        Assertions.assertThat(answerRepository.findById(1L)).isEmpty();

    }
}
