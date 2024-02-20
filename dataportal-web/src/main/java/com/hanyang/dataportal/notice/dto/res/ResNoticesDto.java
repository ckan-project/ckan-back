package com.hanyang.dataportal.notice.dto.res;

import com.hanyang.dataportal.notice.domain.Notice;
import lombok.Data;

import java.time.LocalDate;


@Data
public class ResNoticesDto {
    private Long noticeId;
    private String title;
    private LocalDate createDate;
    private Integer view;
    private String userName;


    public ResNoticesDto(Notice notice) {
        this.noticeId = notice.getNoticeId();
        this.title = notice.getTitle();
        this.createDate = notice.getCreateDate();
        this.view = notice.getView();
        this.userName = notice.getAdmin().getName();

    }
}
