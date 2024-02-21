package com.hanyang.dataportal.notice.dto.res;


import com.hanyang.dataportal.notice.domain.Notice;
import lombok.Data;

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
    private String adminName;

    public ResNoticeDto(Long noticeId, String title, String content, LocalDate createDate, LocalDateTime updateTime, Integer view, String adminName) {
        this.noticeId = noticeId;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.updateTime = updateTime;
        this.view = view;
        this.adminName = adminName;
    }

    public ResNoticeDto(Notice notice) {


    }

    public ResNoticeDto() {

    }

    public static ResNoticeDto toNoticeDto(Notice notice) {
        ResNoticeDto resNoticeDto = new ResNoticeDto();
        resNoticeDto.setNoticeId(notice.getNoticeId());
        resNoticeDto.setTitle(notice.getTitle());
        resNoticeDto.setContent(notice.getContent());
        resNoticeDto.setCreateDate(notice.getCreateDate());
        resNoticeDto.setView(notice.getView());
        resNoticeDto.setAdminName(notice.getAdmin().getName());
        return resNoticeDto;
    }

    public static ResNoticeDto toNoticeDetailDto(Notice notice) {
        ResNoticeDto resNoticeDto = new ResNoticeDto();
        resNoticeDto.setNoticeId(notice.getNoticeId());
        resNoticeDto.setTitle(notice.getTitle());
        resNoticeDto.setContent(notice.getContent());
        resNoticeDto.setCreateDate(notice.getCreateDate());
        resNoticeDto.setView(notice.getView());
        resNoticeDto.setAdminName(notice.getAdmin().getName());
        return resNoticeDto;
    }


    }
