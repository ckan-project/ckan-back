package com.hanyang.dataportal.qna.domain;

import com.hanyang.dataportal.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter

public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;
    private String title;
    @Lob
    private String content;
    private LocalDate date;
    private Integer view;
    @Enumerated(EnumType.STRING)
    private AnswerStatus answerStatus;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;


    @Builder
    public Question(Long questionId, String title, String content, LocalDate date, Integer view, AnswerStatus answerStatus, User user) {
        this.questionId = questionId;
        this.title = title;
        this.content = content;
        this.date = date;
        this.view = view;
        this.answerStatus = answerStatus;
        this.user = user;
    }

    public Question() {

    }
}
