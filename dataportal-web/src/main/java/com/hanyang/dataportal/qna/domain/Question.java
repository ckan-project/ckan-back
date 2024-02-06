package com.hanyang.dataportal.qna.domain;

import com.hanyang.dataportal.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
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
    @OneToOne(mappedBy = "question")
    private Answer answer;
}
