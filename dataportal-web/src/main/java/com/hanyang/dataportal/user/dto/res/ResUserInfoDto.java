package com.hanyang.dataportal.user.dto.res;

import lombok.Data;

@Data
public class ResUserInfoDto {

    private String name;
    private int scrapCount;
    private int downloadCount;

    public ResUserInfoDto(String name, int scrapCount, int downloadCount) {
        this.name = name;
        this.scrapCount = scrapCount;
        this.downloadCount = downloadCount;
    }
}
