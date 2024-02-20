package com.hanyang.dataportal.qna.domain;

import com.hanyang.dataportal.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;
    @Lob
    private String content;
    private LocalDate date;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questionId")
    private Question question;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User admin;

    @Builder
    public Answer(Long answerId, String content, LocalDate date, Question question, User admin) {
        this.answerId = answerId;
        this.content = content;
        this.date = date;
        this.question = question;
        this.admin = admin;
    }

    public Answer() {

    }
}
