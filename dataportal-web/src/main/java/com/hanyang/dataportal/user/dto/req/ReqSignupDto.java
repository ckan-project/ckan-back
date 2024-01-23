package com.hanyang.dataportal.user.dto.req;

import lombok.Data;

@Data
public class ReqSignupDto {
    private String email;
    private String password;
    private String name;
}
