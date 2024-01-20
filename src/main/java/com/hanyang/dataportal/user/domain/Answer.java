package com.hanyang.dataportal.user.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String answerID;

    @OneToOne
    @JoinColumn(name = "questionKey")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private String content;
    private LocalDateTime date;



}
