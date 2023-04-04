package com.jangbogo.domain;

<<<<<<< HEAD
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.jangbogo.domain.common.BaseTimeEntity;
import com.jangbogo.dto.ItemDto;
import com.jangbogo.dto.ProductMypriceRequestDto;
import com.jangbogo.dto.ProductRequestDto;

=======
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
>>>>>>> ed6d9544c8bb75292349dd76fd507a0d1827cbaf
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

<<<<<<< HEAD
@Getter @Setter
@NoArgsConstructor
@Entity
public class Product extends BaseTimeEntity{
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
=======

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
	
>>>>>>> ed6d9544c8bb75292349dd76fd507a0d1827cbaf
	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String image;
	
	@Column(nullable = false)
	private String link;
	
	@Column(nullable = false)
	private Integer lprice;
<<<<<<< HEAD

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
=======
	
	private String mallName;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade =CascadeType.PERSIST)
	@JoinColumn(name = "name")
	private Member user;
	
	@Column(nullable = false)
	private Integer count=0;
	

	@Builder
	public Product (String productId, String title, String image, String link, Integer lprice, String mallName, Member user) {
		this.productId = productId;
		this.title = title;
		this.image = image;
		this.link = link;
		this.lprice = lprice;
		this.mallName = mallName;
		this.user = user;
    }

	public void addCount(Integer count) {
		this.count +=count;
	}





	

	

>>>>>>> ed6d9544c8bb75292349dd76fd507a0d1827cbaf
}
