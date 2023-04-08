package com.jangbogo.domain.product;

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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jangbogo.domain.member.entity.Member;
import com.jangbogo.dto.ProductRequestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Zzim {

    @Id
    @Column(name = "zzimId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY, cascade =CascadeType.PERSIST)
    @JoinColumn(name="email")
    private Member user;
    
    @OneToMany(mappedBy = "zzim", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Product> products = new ArrayList<>();
    
    private Integer count;
 
    public static Zzim creatZzim(Member user){
       Zzim zzim = new Zzim();
    	
       zzim.user=user;
       zzim.count=0;
       return zzim;
       
    }

    public void removeProduct(Long prodId) {
        products.removeIf(product -> product.getId().equals(prodId));

    }
    
    public void addCount(Integer count) {
        if (this.count == null) {
            this.count = 0;
        }
        this.count += count;
    }



}