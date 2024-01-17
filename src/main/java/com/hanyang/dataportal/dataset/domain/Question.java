package com.hanyang.dataportal.dataset.domain;

import com.hanyang.dataportal.user.domain.User;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionKey;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private String title;
    private String content;
    private LocalDate date;
    private String type;
    private Integer view;
    private Status status;
}
