package com.hanyang.dataportal.user.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Setter
@Getter
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
