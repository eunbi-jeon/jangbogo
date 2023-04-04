package com.jangbogo.payload.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateRequest {

    @Schema( type = "string", example = "string", description="계정 명 입니다.")
    @NotBlank
    private String name;

    @Schema( type = "string", example = "string", description="계정 비밀번호 입니다.")
    @NotBlank
    private String password;

    @Schema( type = "string", example = "string", description="계정 연령 정보 입니다.")
    private String age;

    @Schema( type = "string", example = "string", description="계정 지역 정보 입니다.")
    private String region;

}
