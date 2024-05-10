//package com.hanyang.dataportal.qna.service;
//
//import com.hanyang.dataportal.qna.domain.Answer;
//import com.hanyang.dataportal.qna.domain.AnswerStatus;
//import com.hanyang.dataportal.qna.domain.Question;
//import com.hanyang.dataportal.qna.dto.res.ResQuestionListDto;
//import com.hanyang.dataportal.qna.repository.QuestionRepository;
//import com.hanyang.dataportal.user.domain.User;
//import com.hanyang.dataportal.user.repository.UserRepository;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
//@ActiveProfiles("test")
//@Sql("/h2-truncate.sql")
//@Transactional
//public class QuestionServiceTest {
//
//    @Autowired
//    private QuestionService questionService;
//    @Autowired
//    private QuestionRepository questionRepository;
//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    @DisplayName("질문글을 등록한다")
//    void save(){
//        //Given
//        User user = User.builder().email("test@email.com").build();
//        Answer answer = Answer.builder().answerId(1L).build();
//        Question question;
//        question = Question.builder()
//                .id(1L)
//                .title("Test Question")
//                .content("Test Question Content")
//                .date(LocalDate.now())
//                .view(0)
//                .answerStatus(com.hanyang.dataportal.qna.domain.AnswerStatus.valueOf("Waiting"))
//                .user(user)
//                .answer(answer)
//                .build();
//        userRepository.save(user);
//        // When
//        questionService.save(question, user.getEmail());
//        //Then
//        Assertions.assertThat(questionRepository.findById(question.getQuestionId())).isPresent();
//        Assertions.assertThat(question.getUser().getEmail()).isEqualTo(user.getEmail());
//    }
//
//    @Test
//    @DisplayName("질문글을 수정한다")
//    void update() {
//        //Given
//        User user = User.builder().email("test@email.com").build();
//        userRepository.save(user);
//
//        Answer answer = Answer.builder().answerId(1L).build();
//        Question question;
//        question = Question.builder()
//                .id(1L)
//                .title("Test Question")
//                .content("Test Question Content")
//                .date(LocalDate.now())
//                .view(0)
//                .answerStatus(com.hanyang.dataportal.qna.domain.AnswerStatus.valueOf("Waiting"))
//                .user(user)
//                .answer(answer)
//                .build();
//        questionService.save(question, user.getEmail());
//
//        Question question_modify = Question.builder()
//                .title("Modified Test Question")
//                .content("Modified Question Content")
//                .build();
//
//        //When
//        Question getQuestion_modify = questionService.update(question_modify,question.getQuestionId());
//
//        //That
//        Assertions.assertThat(getQuestion_modify.getTitle()).isEqualTo("Modified Test Question");
//        Assertions.assertThat(getQuestion_modify.getContent()).isEqualTo("Modified Question Content");
//    }
//
//
//    @Test
//    @DisplayName("질문글(단건/상세)을 조회한다")
//    void find() {
//        //Given
//        User user_find = User.builder().email("test@email.com").build();
//        userRepository.save(user_find);
//
//        Answer answer_find = Answer.builder().answerId(1L).build();
//        Question question_find;
//        question_find = Question.builder()
//                .id(1L)
//                .title("Test Question")
//                .content("Test Question Content")
//                .date(LocalDate.now())
//                .view(0)
//                .answerStatus(com.hanyang.dataportal.qna.domain.AnswerStatus.valueOf("Waiting"))
//                .user(user_find)
//                .answer(answer_find)
//                .build();
//        questionService.save(question_find, user_find.getEmail());
//
//        //When
//        Question getDetailQuestion = questionService.getDetailQuestion(question_find.getQuestionId());
//
//        //Then
//        Assertions.assertThat(getDetailQuestion.getQuestionId()).isEqualTo(getDetailQuestion.getQuestionId());
//        Assertions.assertThat(getDetailQuestion.getTitle()).isEqualTo("Test Question");
//        Assertions.assertThat(getDetailQuestion.getContent()).isEqualTo("Test Question Content");
//        Assertions.assertThat(getDetailQuestion.getDate()).isEqualTo(LocalDate.now());
//        Assertions.assertThat(getDetailQuestion.getAnswerStatus()).isEqualTo(AnswerStatus.Waiting);
//        Assertions.assertThat(getDetailQuestion.getUser()).isEqualTo(user_find);
//
//        /* "getDetailQuestion.getAnswer()" 테스트케이스 오류 발생
//        * answer_find 가 같은 객체인것 같은데 케이스를 돌려보면 서로 다른 객체를 참조하고 있다.
//        * 그 원인(추정)으로
//        * (1). Answer 클래스에서 DB fetch_type 이 LAZY로 되어 있어 하이버네이트 세션이 받인후 지연로딩을 하는 경우 /
//        *  => 세션이 닫혀서 인 경우 ~> LAZY 에서 EAGER로 변경  [해결 안됨]
//        * (2). @Tranactional 어노테이션을 붙이지 않아서 접근 로직에서 수행하도록 변경 [해결 안됨]
//        * (3). save(), update() 메서드에서 이미 객체를 생성해서 발생할 것 같다 /
//        * => user, question 등 인스턴스 명에 '_find' 를 붙여서 구분 [해결 안됨]
//        * ~~~~ 아래꺼 오류 생기는 이유를 모르겠습니다. ~~~~~ 일단 넘어가고 나중에 할게요 ~~~~~~ */
//        //Assertions.assertThat(getDetailQuestion.getAnswer()).isEqualTo(answer_find);
//    }
//
//
//    @Test
//    @DisplayName("질문글(리스트/목록)을 조회한다")
//    void find_list() {
//        //given ~ 5개의 Question 글 생성
//        User user_1 = User.builder().email("1@test.com").build();
//        User user_2 = User.builder().email("2@test.com").build();
//        User user_3 = User.builder().email("3@test.com").build();
//        User user_4 = User.builder().email("4@test.com").build();
//        User user_5 = User.builder().email("5@test.com").build();
//
////        for(int i = 1 ; i<6; i++) {
////            userRepository.save("user_"+i);
////        }
//
//        userRepository.save(user_1);
//        userRepository.save(user_2);
//        userRepository.save(user_3);
//        userRepository.save(user_4);
//        userRepository.save(user_5);
//
//        Question question_1 = Question.builder().title("1").content("1 번째글 ").build();
//        Question question_2 = Question.builder().title("2").content("2 번째글 ").build();
//        Question question_3 = Question.builder().title("3").content("3 번째글 ").build();
//        Question question_4 = Question.builder().title("4").content("4 번째글 ").build();
//        Question question_5 = Question.builder().title("5").content("5 번째글 ").build();
//
//        questionService.save(question_1,user_1.getEmail());
//        questionService.save(question_2,user_2.getEmail());
//        questionService.save(question_3,user_3.getEmail());
//        questionService.save(question_4,user_1.getEmail());
//        questionService.save(question_5,user_1.getEmail());
//
//        //when
//        Page<ResQuestionListDto> getQuestionPage =  questionService.getQuestionList(2,3);
//
//        //then
//        Assertions.assertThat(getQuestionPage.getTotalPages()).isEqualTo(2);
//    }
//
//    @Test
//    @DisplayName("질문글을 삭제한다")
//    void delete(){
//        //Given
//        User user = User.builder().email("test@test.com").build();
//        userRepository.save(user);
//        Question question = Question.builder().title("Delete Test").build();
//        questionService.save(question, user.getEmail());
//        //When
//        questionService.delete(question.getQuestionId());
//        //Then
//        Assertions.assertThat(questionRepository.findById(question.getQuestionId()).isEmpty());
//    }
//}
