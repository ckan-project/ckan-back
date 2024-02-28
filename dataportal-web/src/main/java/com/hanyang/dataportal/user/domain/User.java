package com.hanyang.dataportal.user.domain;

import com.hanyang.dataportal.notice.domain.Notice;
import com.hanyang.dataportal.qna.domain.Answer;
import com.hanyang.dataportal.qna.domain.Question;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(unique = true)
    private String email;
    private String password;
    @Column(unique = true)
    private String name;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Builder.Default
    private boolean isActive = true;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Scrap> scrapList = new ArrayList<>();
    @OneToMany(mappedBy = "admin", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Notice> noticeList = new ArrayList<>();
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Download> downloadList = new ArrayList<>();
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Question> questionList = new ArrayList<>();
    @OneToMany(mappedBy = "admin", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();

    public void changePassword(String password) {
        this.password = password;
    }
}
