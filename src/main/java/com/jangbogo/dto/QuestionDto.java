package com.jangbogo.dto;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.jangbogo.domain.Board.Board;

import lombok.Data;

@Data
public class QuestionDto {
	
	@NotEmpty(message="제목은 필수 사항입니다.")  //subject 값이 비어있을때 작동 
	@Size (max=200)							//subject 값을 최대 200자까지 
	private String subject; 
	
	@NotEmpty(message="내용은 필수 항목 입니다. ")
	private String content; 
	
    private Board board; 
    
  //  private String region;

}
