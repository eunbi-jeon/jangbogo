package com.jangbogo.domain.product;



import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jangbogo.domain.common.BaseTimeEntity;


import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter @Setter
public class Product extends BaseTimeEntity{
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column(name="prodId")
	private Long id;
	
	private String productId;

	private String title;


	private String image;

	private String link;

	private Integer lprice;

	
	private String mallName;

    
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "zzimId")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "products"})
	private Zzim zzim;
	
	
	@Builder
	public Product (String productId, String title, String image, String link, Integer lprice, String mallName, Zzim zzim) {		
	    this.productId = productId;
	    this.title = title;
	    this.image = image;
	    this.link = link;
	    this.lprice = lprice;
	    this.mallName = mallName;
	    this.zzim = zzim;
	    
	    if (zzim != null ) {
	        zzim.getProducts().add(this);
	    }
	}



	

	

}
