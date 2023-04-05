package com.jangbogo.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavListDto {
  
	private Long favListId;

    private List<ProductRequestDto> requestDtoList;
}
