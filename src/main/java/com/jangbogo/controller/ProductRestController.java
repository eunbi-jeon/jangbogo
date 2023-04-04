package com.jangbogo.controller;

import java.security.Principal;
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
import com.jangbogo.domain.Product;
import com.jangbogo.domain.member.entity.Member;
import com.jangbogo.dto.ProductRequestDto;
import com.jangbogo.repository.MemberRepository;
import com.jangbogo.service.ProductService;
import com.jangbogo.service.auth.AuthService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
public class ProductRestController {
	private final ProductService productService;
	private final MemberRepository memberRepository;
	private final AuthService authService;

	//관심상품 전체 조회
	@GetMapping("/api/products")
		public List<Product> getProductList(@AuthenticationPrincipal Member user){
		Member siteUser = memberRepository.findByName(user.getName())
                .orElseThrow(MemberNotFoundException::new);
	    return productService.getFavList(user);
	}
	
	//관심상품 등록


	@PostMapping("/api/products")
	public ResponseEntity<?> saveFavList(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody ProductRequestDto requestDto) {
	
		Member user = memberRepository.findByEmail(currentUser.getEmail()).orElse(null); // 현재 로그인한 사용자 정보를 가져옴
		ProductRequestDto products = productService.saveProduct(user, requestDto);
	    
	    if(products == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    return new ResponseEntity<>(products, HttpStatus.OK);
	}
	
	//관심상품 삭제
	@DeleteMapping("/api/products/{productId}")
	public void deleteFavList(@AuthenticationPrincipal Member member, @PathVariable String productId) {
        Member user = memberRepository.findByName(member.getName())
                .orElseThrow(MemberNotFoundException::new);
		productService.deleteProduct(user, productId);
	}
	
}
