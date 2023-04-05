package com.jangbogo.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class AnswerDto {
	
	@NotEmpty(message="내용이 비어있습니다. 반드시 입력하세요. ")
	private String content; 
	
}
