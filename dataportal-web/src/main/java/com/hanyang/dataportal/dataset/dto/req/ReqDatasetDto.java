package com.hanyang.dataportal.dataset.dto.req;

import com.hanyang.dataportal.dataset.domain.Dataset;
import com.hanyang.dataportal.dataset.domain.vo.Organization;
import com.hanyang.dataportal.dataset.domain.vo.Theme;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class ReqDatasetDto {

    @Schema(description = "제목", example = "2024 한양대학교 에리카 입학 경쟁률")
    private String title;
    @Schema(description = "설명", example = "2024 한양대학교 에리카 입학 경쟁률에 대한 데이터 입니다.")
    private String description;

    @Schema(description = "조직", example = "입학처")
    private Organization organization;

    @Schema(description = "주제", example = "[\"입학\",\"학생\"]")
    private List<Theme> theme;
    public Dataset toEntity(){
        return Dataset.builder().
                title(title).
                description(description).
                organization(organization).
                view(0).
                download(0).
                build();
    }

}
