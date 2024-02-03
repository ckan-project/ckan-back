package com.hanyang.dataportal.notice.dto.req;


import com.hanyang.dataportal.notice.domain.Notice;
import com.hanyang.dataportal.user.domain.User;
import com.hanyang.dataportal.user.dto.req.ReqSignupDto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static org.hibernate.boot.model.process.spi.MetadataBuildingProcess.build;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqNoticeDto {
    //static ~ class의 공유변수.. final 상수..
    // JVM ~ 메모리 관련
    @NotBlank
    private String title;
    @NotBlank
    private String content;

    public Notice toEntity() {
            return Notice.builder().
                    title(title).
                    content(content).
                    view(0).
                    createDate(LocalDate.now()).
                    build();
    }
}
