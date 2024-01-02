package com.hanyang.dataportal.dataset.dto.req;

import com.hanyang.dataportal.dataset.domain.Dataset;
import com.hanyang.dataportal.dataset.domain.Resource;
import lombok.Data;

@Data
public class ReqResourceDto {

    private String resourceId;
    private String resourceUrl;
    private String type;

    public Resource toEntity(){
        return Resource.builder().
                resourceId(resourceId).
                url(resourceUrl).
                type(type).
                build();
    }

}
