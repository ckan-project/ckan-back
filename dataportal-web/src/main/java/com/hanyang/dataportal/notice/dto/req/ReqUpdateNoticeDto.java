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



    // Dto ~ Controller 에서 Service(Business 로직)으로 데이터를 넘겨줄때 toEntity를 사용
    public Notice toEntity() {
        return Notice.builder().
                title(title).
                content(content).
                createDate(LocalDate.from(LocalDateTime.now())).
                build();
    }

}
