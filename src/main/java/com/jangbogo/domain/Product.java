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
@Getter @Setter
public class Product extends BaseTimeEntity{
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column(name="prodId")
	private Long id;
	
	private String productId;
	
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





	

	

}
