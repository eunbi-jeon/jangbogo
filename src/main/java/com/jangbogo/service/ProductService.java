package com.jangbogo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;


import com.jangbogo.domain.member.entity.Member;
import com.jangbogo.domain.Product;

import com.jangbogo.dto.ProductRequestDto;
import com.jangbogo.repository.MemberRepository;
import com.jangbogo.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class ProductService {

	private static final String Member = null;
	private final ProductRepository productRepository;
	private final MemberRepository memberRepository;

	
	//조회
	public List<Product> getFavList(Member member){
		
		List<Product> productList = new ArrayList<>();
		Product product = productRepository.findByUser(member);
		productList = productRepository.findByProductId(product.getProductId());
		return productList; 
	}
	
	
	// 저장
	@Transactional
	public ProductRequestDto saveProduct(Member user, ProductRequestDto req) {
	  
	    // Product 객체 생성
	    Product product = productRepository.save(buildProductreq(user, req));
	    product.getProductId();
	    return null;
	}


	private Product buildProductreq(Member user, ProductRequestDto req) {
		
		return Product.builder()
				.productId(req.getProductId())
				.title(req.getTitle())
				.image(req.getImage())
				.link(req.getLink())
				.lprice(req.getLprice())
				.mallName(req.getMallName())
				.user(user).build()
				;
	}


	//삭제
	public void deleteProduct(Member member, String productId) {
	    Product product = productRepository.findByUserAndProductId(member, productId);
	    if (product != null) {
	        productRepository.delete(product);
	        
	    }
	}
	

	
}
