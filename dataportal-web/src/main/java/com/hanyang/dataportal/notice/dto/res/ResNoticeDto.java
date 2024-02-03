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
    private LocalDateTime updateTime;
    private Integer view;
    private Long admin;

    public ResNoticeDto(Long noticeId, String title, String content, LocalDate createDate, LocalDateTime updateTime, Integer view, Long admin) {
        this.noticeId = noticeId;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.updateTime = updateTime;
        this.view = view;
        this.admin = admin;
    }

    public ResNoticeDto(Notice notice) {


    }

    public ResNoticeDto() {

    }

    public static ResNoticeDto fromEntity(Notice notice) {
        ResNoticeDto resNoticeDto = new ResNoticeDto();
        resNoticeDto.setNoticeId(notice.getNoticeId());
        resNoticeDto.setTitle(notice.getTitle());
        resNoticeDto.setContent(notice.getContent());
        resNoticeDto.setCreateDate(notice.getCreateDate());
        resNoticeDto.setView(notice.getView());
        resNoticeDto.setAdmin(notice.getAdmin().getUserId());
        return resNoticeDto;
    }
}