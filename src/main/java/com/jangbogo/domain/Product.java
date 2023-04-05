package com.jangbogo.domain;

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
import com.jangbogo.domain.member.entity.Member;
import com.jangbogo.dto.ProductRequestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "favListId")
	private FavList favList;
	
	private Integer count;
	

	@Builder
	public Product (String productId, String title, String image, String link, Integer lprice, String mallName, FavList favList) {
		this.productId = productId;
		this.title = title;
		this.image = image;
		this.link = link;
		this.lprice = lprice;
		this.mallName = mallName;
		this.favList=favList;
		
		if (!favList.getProducts().contains(this))
			favList.getProducts().add(this);
    }

	public void addCount(Integer count) {
		this.count +=count;
	}
	
    public void updateCount(Integer count){
        this.count = count;
    }






	

	

}
