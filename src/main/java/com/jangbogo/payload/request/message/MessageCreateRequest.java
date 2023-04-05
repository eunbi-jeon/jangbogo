package com.jangbogo.payload.request.message;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageCreateRequest {

    @NotBlank(message = "메시지 제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "메시지 내용을 입력해주세요.")
    private String content;

    @NotNull(message = "받는 사람 닉네임을 입력해주세요")
    private String receiverNickname;
}
