package com.jangbogo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductRequestDto {
		private Long id;
		private Integer productId;
		private String title;
	    private String link;
	    private String image;
	    private String mallName;
	    private Integer lprice;
	    private String category1;
	    private String category2;
	    private String category3;
	    private String category4;

	    
}
