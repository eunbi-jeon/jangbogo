package com.jangbogo.dto;

import com.jangbogo.domain.Message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
	private String title;
	private String content;
	private String senderName;
	private String receiverName;

	public static MessageDTO toDto(Message message) {
		return new MessageDTO(message.getTitle(), message.getContent(), message.getSender().getNickName(),
				message.getReceiver().getNickName());
	}
}
