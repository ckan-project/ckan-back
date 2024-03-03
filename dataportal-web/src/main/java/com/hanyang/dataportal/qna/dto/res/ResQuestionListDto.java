package com.hanyang.dataportal.qna.dto.res;


import com.hanyang.dataportal.qna.domain.Question;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@Data
@Setter
public class ResQuestionListDto {

    /* 총페이지, 총데이터, 질문리스트 */
    private int totalPages;
    private long totalData;
    private int page;
    private List<ResRtnQuestionListDto> resRtnQuestionListDtos;

    // 기본생성자
    public ResQuestionListDto(int totalPages, long totalElements) {

    }

    //생성자를 수정...?
    public ResQuestionListDto(int totalPages, long totalData, int page, List<ResRtnQuestionListDto> resRtnQuestionListDtos) {
        this.totalPages = totalPages;
        this.totalData = totalData;
        this.resRtnQuestionListDtos = resRtnQuestionListDtos;
        this.page = page;
    }

    // setter 방식은 @Data를 사용하면 이미 생성되므로, 직접 호출을 할 필요는 없다...
    public static ResQuestionDto toResQuestionListDto(Question question) {
    ResQuestionDto resQuestionDto = new ResQuestionDto();
    resQuestionDto.setQuestionId(question.getQuestionId());
    resQuestionDto.setTitle(question.getTitle());
    resQuestionDto.setView(question.getView());
    resQuestionDto.setAnswerStatus(question.getAnswerStatus());
    return resQuestionDto;
    }
}


//    private Long questionId;
//    private String title;
//    @Lob
//    private String content;
//    private LocalDate date;
//    private Integer view;
//    @Enumerated(EnumType.STRING)
//    private AnswerStatus answerStatus;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "userId")
//    private User user;
//    @OneToOne(mappedBy = "question",cascade = CascadeType.ALL)
//    private Answer answer;