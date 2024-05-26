package com.hanyang.dataportal.user.dto.res;

import com.hanyang.dataportal.user.domain.User;
import lombok.Data;

@Data
public class ResUserInfoDto {

    private String name;
    private String email;
    private int scrapCount;
    private int downloadCount;

    public ResUserInfoDto(User user, int scrapCount, int downloadCount) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.scrapCount = scrapCount;
        this.downloadCount = downloadCount;
    }
}
