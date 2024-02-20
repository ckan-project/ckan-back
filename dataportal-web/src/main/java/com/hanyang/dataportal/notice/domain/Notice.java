package com.hanyang.dataportal.notice.domain;

import com.hanyang.dataportal.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;
    
    private String title;

    private String content;
    
    private LocalDate createDate;
    private LocalDate  updateDate;
    private Integer view;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User admin;

    //기본생성자 ~ constructor 
    public Notice() {

    }

    //AllargConstructor ~ 개방폐쇄원칙?
    @Builder
    public Notice(Long noticeId, String title, String content, LocalDate createDate, Integer view, User admin) {
        this.noticeId = noticeId;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.view = view;
        this.admin = admin;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreateDate(LocalDate now) {
        this.createDate= now;
    }

    public void setview(int i) {
        this.view = i;

    }

    public void setUser(User user) {
        this.admin = user;
    }

    public void setUpdateDateTime(LocalDate now) {
        this.updateDate = now;
    }
}