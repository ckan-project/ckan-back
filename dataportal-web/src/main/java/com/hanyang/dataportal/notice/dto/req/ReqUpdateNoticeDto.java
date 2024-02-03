package com.hanyang.dataportal.notice.dto.req;


import com.hanyang.dataportal.notice.domain.Notice;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ReqUpdateNoticeDto {

    @NotBlank
    private String title;
    @NotBlank
    private String content;


    public Notice toEntity() {
        return Notice.builder().
                title(title).
                content(content).
                updateDateTime(LocalDateTime.now()).
                build();
    }

}
