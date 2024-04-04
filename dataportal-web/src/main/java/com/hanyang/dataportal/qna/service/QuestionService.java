package com.hanyang.dataportal.qna.service;

import com.hanyang.dataportal.core.exception.ResourceNotFoundException;
import com.hanyang.dataportal.qna.domain.Question;
import com.hanyang.dataportal.qna.dto.res.ResQuestionListDto;
import com.hanyang.dataportal.qna.repository.QuestionRepository;
import com.hanyang.dataportal.user.domain.User;
import com.hanyang.dataportal.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service (value = "질문하기 서비스")
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserService userService;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question save(Question question, String username) {
        // 1. userservice에서 유저를 찾아
        User user = userService.findByEmail(username);
        question.setUser(user);
        // 2. Question에 User 정보를 등록
//        Question question = (Question) questionRepository.findByUser(username);
        return questionRepository.save(question);
    }

    public Question updateQuestion(Question reqUpdateQuestionDto, long questionId) {
        // 1. userservice에서 유저를 찾아
        Optional<Question> updateQuestion = questionRepository.findById(questionId);
        if (updateQuestion.isPresent()) {
            questionRepository.save(reqUpdateQuestionDto);
        } else {
            throw new ResourceNotFoundException("수정할 질문글이 없습니다.");
        }
        return reqUpdateQuestionDto;
    }

    public Question getDetailQuestion(Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("조회된 질문글이 없습니다."));
    }

    public List<Question> getAllQuestion() {
        return questionRepository.findAll();
    }

//    public List<Question> getAllMyQuestion(String user) {
//        return questionRepository.findByUser(user);
//    }

    public boolean deleteQuestion(Long questionId) {
        // 1. userservice에서 유저를 찾아
        // 2. questionId로 question을 찾아
        // 3. question의 작성자가 위에 파라미터로 들어온 username인지 확인 -> 다르면 예외 발생
     if(questionRepository.existsById(questionId)){ // throw 로 확실히 예외처리를 하겠습니다.
         questionRepository.deleteById(questionId);
         return true;
        } else return false;
    }

    /* Completed 된 Question의 글 중에서 페이징 수를 가져오는 부분 ...  */
    /*  Pageable 인터페이스
    getPagenumber() 현재 페이지 번호를 반환, getPageSize()한 페이지당 최대 항목수, getOffset()현재 페이지의 시작위치,
    getSort() 정렬정보, next() 다음 페이지 정보, previous()이전 페이지 번호
    getContent() */

    public Page<ResQuestionListDto> getQuestionList(int pageNum, int listSize) {
        Pageable pageable = PageRequest.of(pageNum-1, listSize, Sort.by("date").descending());
        Page<Question> questions = questionRepository.findAll(pageable);

        Page<ResQuestionListDto> resQuestionDtoList = questions.map(ResQuestionListDto::toDto);
            /*
        each-for 문 사용법
        for (type 변수명: iterate) {
            body-of-loop }
       */
//        for (Question question : questions) {
//            ResQuestionListDto resQuestionListDto = ResQuestionListDto.toDto(question);
//            resQuestionDtoList.add(resQuestionListDto);
//        }
        return resQuestionDtoList;
    }

    // 나의 정보 불러오기는 pageable에다가 criteria 을 함께 넣어주면 됨..! 에서 userName을 찾아와서 찾아주는 방식으로 구현할 생각이었으나,
    // question에서는 게시글을 username으로 저장하므로... 그냥 바로 questionRepository.findByUser에서 찾아와도 될듯... ?

    public List<ResQuestionListDto> getMyQuestionList(String userName, int pageNum, int listSize) {
        User user = userService.findByEmail(userName);
        Pageable pageable = PageRequest.of(pageNum-1, listSize, Sort.by("date").descending());
        Page<Question> questions = questionRepository.findByUser(user,pageable);
        questions.getContent();
        questions.getTotalElements();
        questions.getTotalPages();
        /*
        each-for 문 사용법
        for (type 변수명: iterate) {
            body-of-loop }
       */
        List<ResQuestionListDto> resQuestionList = new ArrayList<>();
        for(Question question : questions.getContent()) {
            resQuestionList.add(ResQuestionListDto.toDto(question));
        }
        return resQuestionList;
    }
}

