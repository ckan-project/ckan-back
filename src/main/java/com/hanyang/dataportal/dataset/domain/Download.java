package com.hanyang.dataportal.dataset.domain;

import com.hanyang.dataportal.user.domain.User;
import jakarta.persistence.*;

@Entity
public class Download {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long downloadId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "resourceKey")
    private Resource resource;
}
