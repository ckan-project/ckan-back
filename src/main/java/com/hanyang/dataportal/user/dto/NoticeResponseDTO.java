package com.hanyang.dataportal.user.dto;

import com.hanyang.dataportal.user.domain.Notice;
import com.hanyang.dataportal.user.repository.NoticeRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
public class NoticeResponseDTO {

    private Long noticeId;
    private String title;
    private String content;
    private LocalDate createDate;
    private Integer view;

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;

    }

    //아~ 여기 뭐지? 이해가 안된다.
    //noticeservice ~ 에서 notice1 객체를 단일뷰페이지에 (notice, title, content. createDate, view) 정보를
    // ResponseDTO 규격에 맞춰서 프론트로 던지기 전에 작업인데.. set? 이 안되는 이유?를 모르겠다.
    public static NoticeResponseDTO toNoticeResponseDTO(Notice notice){
        NoticeResponseDTO noticeResponseDTO = new NoticeResponseDTO();
        noticeResponseDTO.setNoticeId(notice.getId());
        noticeResponseDTO.setTitle(notice.getTitle());
        noticeResponseDTO.setContent(notice.getContent());
        noticeResponseDTO.setCreateDate(notice.getDate());
        noticeResponseDTO.setView(notice.getView());
        return noticeResponseDTO;



    }

}
