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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jangbogo.domain.common.BaseTimeEntity;
import com.jangbogo.domain.member.entity.Member;
import com.jangbogo.dto.ProductRequestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter @Setter
public class Product{
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Integer productId;
	
	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String image;
	
	@Column(nullable = false)
	private String link;
	
	@Column(nullable = false)
	private Integer lprice;
	
	private String mallName;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade =CascadeType.PERSIST)
	@JoinColumn(name = "member_id")
	private Member member;
	
	@Column(nullable = false)
	private Integer count=0;
	
	
	
	//관심상품
	public static Product createList(Member member) {
		Product product = new Product();
		member.addProduct(product);
		product.setMember(member);
		return product;
	}

	public Product(ProductRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.image = requestDto.getImage();
        this.link = requestDto.getLink();
        this.lprice = requestDto.getLprice();
        this.mallName=requestDto.getMallName();
 
    }

	public void addCount(Integer count) {
		this.count +=count;
	}
	public void updateCount(Integer count) {
		this.count = count;
	}
	

}
