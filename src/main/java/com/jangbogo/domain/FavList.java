package com.jangbogo.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.jangbogo.domain.member.entity.Member;
import com.jangbogo.dto.ProductRequestDto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity
@Data
public class FavList {
    @Id
    @Column(name = "favListId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
    @OneToOne(fetch = FetchType.LAZY, cascade =CascadeType.PERSIST)
    @JoinColumn(name="email")
    private Member user;
    
    @OneToMany(mappedBy = "prodId", cascade = CascadeType.ALL
    		, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

 
    @Builder
    public FavList(Member user, List<Product> products){
    	this.user=user;
    	this.products=products;
    }

    public void removeProduct(String productId) {
        products.removeIf(product -> product.getProductId().equals(productId));
    }


}
