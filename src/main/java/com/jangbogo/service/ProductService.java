package com.jangbogo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.stereotype.Service;


import com.jangbogo.domain.member.entity.Member;
import com.jangbogo.domain.FavList;
import com.jangbogo.domain.Product;
import com.jangbogo.dto.FavListDto;
import com.jangbogo.dto.ProductRequestDto;
import com.jangbogo.repository.FavListRepository;
import com.jangbogo.repository.MemberRepository;
import com.jangbogo.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class ProductService {


	private final ProductRepository productRepository;
	private final MemberRepository memberRepository;
	private final FavListRepository favListRepository;

	//fav리스트 조회
	public FavList setFavList(Member user) {

	      return favListRepository.findByUser(user);
               
	}
	
	//fav 리스트 내부 조회
	public List<Product> getFavList(Member user){
		
		List<Product> productList = new ArrayList<>();
		
		Product product = productRepository.findByUser(user);
		
		productList = productRepository.findByProductId(product.getProductId());
		return productList; 
	}
	
	
	// 저장
	@Transactional
	public ProductRequestDto saveProduct(ProductRequestDto req) {
	  

	    Product product = productRepository.save(buildProductreq(req));
	    product.getProductId();
	    return null;
	}

	private Product buildProductreq(ProductRequestDto req) {
		
		return Product.builder()
				.productId(req.getProductId())
				.title(req.getTitle())
				.image(req.getImage())
				.link(req.getLink())
				.lprice(req.getLprice())
				.mallName(req.getMallName())
				.build()
				;
	}

	
	//삭제
	public void deleteProduct(Member user, String productId) {
		FavList favList = setFavList(user);
	    favList.removeProduct(productId);
	    favListRepository.save(favList);
	        
	    }

}
