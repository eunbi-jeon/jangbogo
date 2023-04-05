package com.jangbogo.domain.Product;



import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.jangbogo.domain.common.BaseTimeEntity;


import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.Getter;
import lombok.NoArgsConstructor;



@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
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
	private Zzim zzim;
	
    private Integer count;
	

	@Builder
    public Product (String productId, String title, String image, String link, Integer lprice, String mallName, Zzim zzim) {		
		this.productId = productId;
		this.title = title;
		this.image = image;
		this.link = link;
		this.lprice = lprice;
		this.mallName = mallName;
        this.zzim=zzim;
        
        if (!zzim.getProducts().contains(this))
        	zzim.getProducts().add(this);
	}

	public void addCount(Integer count) {
		this.count +=count;
	}




	

	

}
