package com.hanyang.dataportal.dataset.dto.res;

import com.hanyang.dataportal.dataset.domain.Notice;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ResNoticeDto {
    private Long noticeId;
    private String title;
    private String content;
    private String createDate;
    private Integer view;
    private Long userId;

    public ResNoticeDto(Notice notice) {
        this.noticeId = notice.getNoticeId();
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.createDate = notice.getCreateDate().toString();
        this.view = notice.getView();
        this.userId = notice.getUser().getUserId();
    }
}
