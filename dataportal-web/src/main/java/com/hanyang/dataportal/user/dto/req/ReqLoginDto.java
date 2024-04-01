package com.hanyang.dataportal.user.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ReqLoginDto {
    @Schema(example = "testUser")
    private String email;
    @Schema(example = "1234")
    private String password;
    @Schema(example = "false")
    private Boolean autoLogin;
}
