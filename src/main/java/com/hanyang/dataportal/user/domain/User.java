package com.hanyang.dataportal.user.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.lang.management.LockInfo;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userKey;

    private String id;

    private String password;

    private String email;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Follow> followList = new ArrayList<>();


}
