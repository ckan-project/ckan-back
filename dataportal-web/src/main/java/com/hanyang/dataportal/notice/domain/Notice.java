package com.hanyang.dataportal.notice.domain;

import com.hanyang.dataportal.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;
    
    private String title;

    private String content;
    
    private LocalDate createDate;
    private LocalDateTime  updateDateTime;
    private Integer view;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User admin;

    //기본생성자 ~ constructor 
    public Notice() {

    }

    //AllargConstructor ~ 개방폐쇄원칙?
    public Notice(Long noticeId, String title, String content, LocalDate createDate, Integer view, User admin) {
        this.noticeId = noticeId;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.view = view;
        this.admin = admin;
    }

    public void setTitle(String title) {
    }

    public void setContent(String content) {
    }

    public void setCreateDate(LocalDateTime now) {

    }

    public void setview(int i) {

    }

    public void setUser(String user) {
    }

    public void setUpdateDateTime(LocalDateTime now) {

    }
}