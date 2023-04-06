package com.jangbogo.dto;


<<<<<<< HEAD
import com.jangbogo.domain.Product;

=======
import com.jangbogo.domain.Product.Product;
import com.jangbogo.domain.member.entity.Member;
>>>>>>> be985e41549ba282b5d80546d617aeb64b2a5333

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

=======
  


>>>>>>> be985e41549ba282b5d80546d617aeb64b2a5333
}
