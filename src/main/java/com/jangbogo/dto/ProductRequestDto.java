package com.jangbogo.dto;

<<<<<<< HEAD
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductRequestDto {
		private String title;
	    private String link;
	    private String image;
	    private Integer lprice;
=======
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
>>>>>>> ed6d9544c8bb75292349dd76fd507a0d1827cbaf
}
