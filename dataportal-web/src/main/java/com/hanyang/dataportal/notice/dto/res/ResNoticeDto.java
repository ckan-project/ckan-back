package com.hanyang.dataportal.notice.dto.res;


import com.hanyang.dataportal.notice.domain.Notice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ResNoticeDto {

    private Long noticeId;
    private String title;
    private String content;
    private LocalDate createDate;
    private LocalDate updateTime;
    private Integer view;
    private String userName;

    public ResNoticeDto(Notice notice) {
        this.noticeId = notice.getNoticeId();
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.createDate = notice.getCreateDate();
        this.updateTime = notice.getUpdateDate();
        this.view = notice.getView();
        this.userName = notice.getAdmin().getName();
    }

}