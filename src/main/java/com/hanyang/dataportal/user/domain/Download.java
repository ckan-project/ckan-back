package com.hanyang.dataportal.user.domain;


import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "Download")
public class Download {

    @Id
    private String downloadId;

    @ManyToOne
    @JoinColumn(name = "userId")
    //entity간 연관관계를 건다?
    private User user;

    // 의존성을 낮출 때?
    // private Long userId;

    @ManyToOne
    @JoinColumn(name = "resourceKey")
    private Resource resource;


}
