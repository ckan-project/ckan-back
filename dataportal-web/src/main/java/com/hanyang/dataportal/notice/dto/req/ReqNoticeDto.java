package com.hanyang.dataportal.notice.dto.req;


import com.hanyang.dataportal.notice.domain.Notice;
import com.hanyang.dataportal.notice.domain.NoticeLabel;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqNoticeDto {
    @NotBlank
    private String title;
    @NotBlank
    private String content;

    @NotBlank
    private NoticeLabel noticeLabel;

    public Notice toEntity() {
        return Notice.builder().
                title(title).
                content(content).
                noticeLabel(noticeLabel).
                build();
    }
}
