package com.jangbogo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.jangbogo.constant.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Favorite {
	@Id
	@GeneratedValue(strategy = GenerationType
	.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String productId;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private Integer lprice;

	private Integer hprice;

	
	private String brand;

	private String category1;

	private String category2;

	private String category3;

	private String category4;

	@Column(nullable = false)
	private String link;

	private String maker;

	private String mallName;

	private String image;

	private Role role;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@Builder
	public Favorite(String title, Integer lprice, Integer hprice, String brand, String category1, String category2,
			String category3, String category4, String link, String maker, String mallName, String image) {

		this.title = title;
		this.lprice = lprice;
		this.brand = brand;
		this.category1 = category1;
		this.category2 = category2;
		this.category3 = category3;
		this.category4 = category4;
		this.link = link;
		this.maker = maker;
		this.mallName = mallName;
		this.image = image;
		this.role = Role.ADMIN;
	}


}
