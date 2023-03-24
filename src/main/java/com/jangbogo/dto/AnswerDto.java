package com.jangbogo.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AnswerDto {
	
	@NotEmpty(message="내용을 반드시 입력해주세요.")
	private String content;
	
}
