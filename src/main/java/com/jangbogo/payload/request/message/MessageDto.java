package com.jangbogo.payload.request.message;

import com.jangbogo.domain.DirectMessage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private Long id;
    private String title;
    private String content;
    private String senderName;
    private String receiverName;

    public static MessageDto toDto(DirectMessage message) {
        return new MessageDto(
                message.getId(),
                message.getTitle(),
                message.getContent(),
                message.getSender().getName(),
                message.getReceiver().getName()
        );
    }


}
