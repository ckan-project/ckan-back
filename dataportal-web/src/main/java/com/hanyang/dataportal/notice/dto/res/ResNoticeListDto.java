package com.hanyang.dataportal.notice.dto.res;

import com.hanyang.dataportal.notice.domain.Notice;
import com.hanyang.dataportal.user.domain.User;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ResNoticeListDto {


    private Long noticeId;
    private String title;
    private LocalDate createDate;
    private Integer view;
    private String adminName;

    public ResNoticeListDto(Long noticeId, String title, LocalDate createDate, Integer view, String adminName) {
        this.noticeId = noticeId;
        this.title = title;
        this.createDate = createDate;
        this.view = view;
        this.adminName = adminName;
    }

    public ResNoticeListDto() {

    }

    public static ResNoticeListDto toResNoticeListDto(Notice notice) {
        ResNoticeListDto resNoticeListDto = new ResNoticeListDto();
        resNoticeListDto.setNoticeId(notice.getNoticeId());
        resNoticeListDto.setTitle(notice.getTitle());
        resNoticeListDto.setCreateDate(notice.getCreateDate());
        resNoticeListDto.setView(notice.getView());

        // resNoticeListDto.setAdminName(notice.getAdmin().getName());
        /* user가 없는 게시글의 경우 NULL Point Exception이 발생하여 예외처리 */
        User admin = notice.getAdmin();
        if (admin != null) {
            resNoticeListDto.setAdminName(admin.getName());
        } else {
            resNoticeListDto.setAdminName("유저정보 없음");

        }
        return resNoticeListDto;
    }

}
