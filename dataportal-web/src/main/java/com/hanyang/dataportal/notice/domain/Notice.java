package com.hanyang.dataportal.notice.domain;

import com.hanyang.dataportal.notice.dto.res.ResNoticeDto;
import com.hanyang.dataportal.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;

    private String title;

    private String content;

    private LocalDate createDate;
    private LocalDateTime updateDateTime;
    private Integer view;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User admin;

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public void setUpdateNotice(Notice reqNoticeDto) {

        this.title = reqNoticeDto.getTitle();
        this.content = reqNoticeDto.getContent();
        this.updateDateTime = LocalDateTime.now();

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }


    public static ResNoticeDto toNoticeUpdateDto(Notice notice) {
        ResNoticeDto resNoticeDto = new ResNoticeDto();
        resNoticeDto.setNoticeId(notice.getNoticeId());
        resNoticeDto.setTitle(notice.getTitle());
        resNoticeDto.setContent(notice.getContent());
        resNoticeDto.setUpdateTime(notice.getUpdateDateTime());
        resNoticeDto.setView(notice.getView());
        resNoticeDto.setAdminName(notice.getAdmin().getName());
        return resNoticeDto;
    }
}