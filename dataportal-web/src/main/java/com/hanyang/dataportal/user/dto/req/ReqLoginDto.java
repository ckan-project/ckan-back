package com.hanyang.dataportal.user.dto.req;

import lombok.Data;

@Data
public class ReqLoginDto {
    private String email;
    private String password;
}
