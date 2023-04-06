package com.jangbogo.dto;


import com.jangbogo.domain.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ProductRequestDto {

	private String productId;
    private String title;
    private String link;
    private String image;
    private String mallName;
    private Integer lprice;
    private String name;
    


	public static ProductRequestDto toDto(Product product) {
		return new ProductRequestDto (
	
				product.getProductId(),
				product.getTitle(),
		    	product.getLink(),
		    	product.getImage(),
		    	product.getMallName(),
		    	product.getLprice(),
		    	product.getUser().getName()
		   
	    	);
	}

}
