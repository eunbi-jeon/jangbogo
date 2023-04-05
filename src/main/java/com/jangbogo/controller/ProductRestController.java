package com.jangbogo.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jangbogo.advice.MemberNotFoundException;
import com.jangbogo.config.security.token.CurrentUser;
import com.jangbogo.config.security.token.UserPrincipal;
import com.jangbogo.domain.FavList;
import com.jangbogo.domain.Product;
import com.jangbogo.domain.member.entity.Member;
import com.jangbogo.dto.ProductRequestDto;
import com.jangbogo.repository.FavListRepository;
import com.jangbogo.repository.MemberRepository;
import com.jangbogo.service.ProductService;


import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
public class ProductRestController {
	private final ProductService productService;
	private final MemberRepository memberRepository;
	private final FavListRepository favListRepository;

	//관심상품 전체 조회
	@GetMapping("/api/products")
		public ResponseEntity<?> getFavList(@CurrentUser UserPrincipal currentUser){
		Member user = memberRepository.findByEmail(currentUser.getEmail()).orElse(null); 
		FavList favList = favListRepository.findByUser(user);
		List<Product> productList = favList.getProducts();
		return ResponseEntity.ok(productList);
	}
	
	//관심상품 등록
	@PostMapping("/api/products")
	public ResponseEntity<?> saveFavList(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody ProductRequestDto requestDto) {
	
		Member user = memberRepository.findByEmail(currentUser.getEmail()).orElse(null); 
		FavList favList = productService.setFavList(user);
		ProductRequestDto product = productService.saveProduct(requestDto);

	    
			if(product == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    return new ResponseEntity<>(product, HttpStatus.OK);
	}
//	
//
//	@PostMapping("/api/products")
//	public ResponseEntity<?> saveFavList(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody ProductRequestDto requestDto) {
//	
//		Member user = memberRepository.findByEmail(currentUser.getEmail()).orElse(null); // 현재 로그인한 사용자 정보를 가져옴
//		ProductRequestDto products = productService.saveProduct(user, requestDto);
//	    
//	    if(products == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//	    return new ResponseEntity<>(products, HttpStatus.OK);
//	}
//	

	
	
	//관심상품 삭제
	@DeleteMapping("/api/products/{productId}")
	public ResponseEntity<?> deleteFavList(@CurrentUser UserPrincipal currentUser, @PathVariable String productId) {
		Member user = memberRepository.findByEmail(currentUser.getEmail()).orElse(null); 
		FavList favList = productService.setFavList(user);
		
		productService.deleteProduct(user, productId);
		
			if(favList == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    return new ResponseEntity<>(favList, HttpStatus.OK);
	}
	
}
