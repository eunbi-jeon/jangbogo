package com.jangbogo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.jangbogo.domain.common.BaseTimeEntity;
import com.jangbogo.dto.ItemDto;
import com.jangbogo.dto.ProductMypriceRequestDto;
import com.jangbogo.dto.ProductRequestDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@Entity
public class Product extends BaseTimeEntity{
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String image;
	
	@Column(nullable = false)
	private String link;
	
	@Column(nullable = false)
	private Integer lprice;

	@Column(nullable = false)
	private Integer myprice;
	
	//관심 상품
	public Product(ProductRequestDto requestDto) {
		this.title = requestDto.getTitle();
		this.image = requestDto.getImage();
		this.link = requestDto.getLink();
		this.lprice = requestDto.getLprice();
		this.myprice = 0 ;
		
	}
	
	//관심 가격 업데이트
	public void update(ProductMypriceRequestDto requestDto) {
		this.myprice = requestDto.getMyprice();
	}
	
	//예약시간 가격 업데이트
	public void updateByItemDto(ItemDto itemDto) {
		this.lprice = itemDto.getLprice();
	}
}
