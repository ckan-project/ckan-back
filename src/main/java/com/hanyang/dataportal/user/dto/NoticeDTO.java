package com.hanyang.dataportal.user.dto;


import com.hanyang.dataportal.user.domain.Notice;
import com.hanyang.dataportal.user.domain.User;
import lombok.Builder;
import lombok.Data;

@Data
public class NoticeDTO {
    private Long userId;
    private String title;
    private String content;



    public  Notice toNoticeEntity() {
       Notice notice = new Notice();
       notice.setTitle(this.title);
       notice.setContent(this.content);

       return notice;
    }



    // 노티스디티오를 노티스로 변환을..



}
