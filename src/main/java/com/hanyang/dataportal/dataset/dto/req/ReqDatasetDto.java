package com.hanyang.dataportal.dataset.dto.req;

import com.hanyang.dataportal.dataset.domain.Dataset;
import lombok.Data;

import java.util.List;

@Data
public class ReqDatasetDto {

    private String title;
    private String description;
    private String organization;
    private List<String> themes;

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
