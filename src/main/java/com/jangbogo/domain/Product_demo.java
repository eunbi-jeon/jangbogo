package com.jangbogo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.jangbogo.dto.ProductRequestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter 
@NoArgsConstructor 
@AllArgsConstructor
@Entity 
public class Product_demo {


	    @GeneratedValue(strategy = GenerationType.AUTO)
	    @Id
	    private Long id;

	    @Column(nullable = false)
	    private String title;

	    @Column(nullable = false)
	    private String image;

	    @Column(nullable = false)
	    private String link;

	    @Column(nullable = false)
	    private int lprice;

		private String mallName;
		

	    public Product_demo(ProductRequestDto requestDto) {
	        this.title = requestDto.getTitle();
	        this.image = requestDto.getImage();
	        this.link = requestDto.getLink();
	        this.lprice = requestDto.getLprice();
	        this.mallName=requestDto.getMallName();
	    }
}
