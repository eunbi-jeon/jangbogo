package com.jangbogo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDto {

    //토큰의 값을 헤더에서 뽑거나 헤더에서 삽입할때 쓰는 dto

    private String grantType;
    private String accessToken;
    private Long tokenExpiresIn;
}
