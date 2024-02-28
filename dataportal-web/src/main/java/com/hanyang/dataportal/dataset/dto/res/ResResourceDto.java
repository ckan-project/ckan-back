package com.hanyang.dataportal.dataset.dto.res;

import com.hanyang.dataportal.dataset.domain.Resource;
import com.hanyang.dataportal.dataset.domain.Type;
import lombok.Data;

@Data
public class ResResourceDto {
    private Long resourceId;
    private String resourceUrl;
    private Type type;
    private String resourceName;

    public ResResourceDto(Resource resource) {
        this.resourceId = resource.getResourceId();
        this.resourceUrl = resource.getResourceUrl();
        this.type = resource.getType();
        this.resourceName = resource.getResourceName();
    }
}
