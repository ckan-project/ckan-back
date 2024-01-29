package com.hanyang.dataportal.user.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ReqScrapDto {
    @Schema(description = "스크랩 하는 데이터셋의 id값(숫자)", example = "1")
    private Long datasetId;
}
