package com.hanyang.dataportal.dataOffer.domain;


import com.hanyang.dataportal.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Builder
@Getter@NoArgsConstructor
@AllArgsConstructor

public class DataOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    private String name;  // 성명
    private LocalDate birthDay;  // 생년월일
    private String phoneNumber;  // 전화번호
    private String email;  // 이메일주소

    @Lob
    private String requestContent;  // 신청내용
    private String dataName;  // 공공데이터명
    private String organizationName;  // 기관명
    @Lob
    private String dataContent;  // 공공데이터내용
    private String purpose;  // 활용목적
    @Lob
    private String purposeContent;  // 데이터활용목적

    private LocalDate date;

    public void setAdmin(User user) {
        this.user = user;
    }
}

//~ 데이터 요청
//        유저정보
//        신청인정보
//        성명
//        생년월일
//        전화번호
//        이메일주소
//
//        신청내용
//        공공데이터명
//        기관명
//        공공데이터내용
//        활용목적
//        데이터활용목적
//
//        생성일자
//        동의여부