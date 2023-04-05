package com.jangbogo.dto;


import com.jangbogo.domain.Product;
import com.jangbogo.domain.member.entity.Member;

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
  


	
<<<<<<< HEAD
=======
				product.getProductId(),
				product.getTitle(),
		    	product.getLink(),
		    	product.getImage(),
		    	product.getMallName(),
		    	product.getLprice(),
		    	product.getUser().getName()
		   
	    	);
	}

>>>>>>> 66ef775d76714e9a80c91047f09a43d4296be784
}
