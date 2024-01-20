package com.hanyang.dataportal.user.domain;


import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "Question")
public class Question {

    @Id
    private String questionKey;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private String title;
    private String content;
    private LocalDate date;
    private String type;
    private int view;
    private String status;




}
