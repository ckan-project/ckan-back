package com.hanyang.dataportal.dataset.domain;

import com.hanyang.dataportal.user.domain.User;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;

    private String title;
    private String content;
    private LocalDate createDate;
    private Integer view;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}
