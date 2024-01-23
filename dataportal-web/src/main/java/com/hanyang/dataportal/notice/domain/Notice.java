package com.hanyang.dataportal.notice.domain;

import com.hanyang.dataportal.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;
    private String title;
    private String content;
    private LocalDate createDate;
    private Integer view;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User admin;
}
