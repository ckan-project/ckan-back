package com.hanyang.dataportal.dataset.domain;

import com.hanyang.dataportal.user.domain.User;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    @OneToOne
    @JoinColumn(name = "questionKey")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    private String content;
    private LocalDate date;
}
