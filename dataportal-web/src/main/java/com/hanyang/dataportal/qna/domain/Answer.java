package com.hanyang.dataportal.qna.domain;

import com.hanyang.dataportal.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;
    @Lob
    private String answerContent;
    private String answerTitle;
    private LocalDate answerdate;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "questionId")
    private Question question;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User admin;



//    public void setQuestionId(Long questionId) {
//        this.questionId = questionId;
//    }
//
//    public Long getQuestionId() {
//        return questionId;
//    }
}
