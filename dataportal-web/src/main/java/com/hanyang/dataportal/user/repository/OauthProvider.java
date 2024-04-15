package com.hanyang.dataportal.user.repository;

import com.hanyang.dataportal.user.dto.res.OauthUserDto;
import org.springframework.stereotype.Component;

@Component
public interface OauthProvider {
    OauthUserDto getUserInfo(String code);
    String getProvider();
}
