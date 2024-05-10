package com.hanyang.dataportal.qna.domain;

import com.hanyang.dataportal.qna.dto.req.ReqQuestionDto;
import com.hanyang.dataportal.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;
    private String title;
    @Lob
    private String content;
    private LocalDate createDate;
    private Integer view;
    @Enumerated(EnumType.STRING)
    private AnswerStatus answerStatus;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;
    @OneToOne(mappedBy = "question", cascade = CascadeType.ALL)
    private Answer answer;
    public void setUser(User user) {
        this.user = user;
    }

    public void update(ReqQuestionDto reqQuestionDto){
        this.title = reqQuestionDto.getContent();
        this.content = reqQuestionDto.getContent();
    }

}
