package com.jangbogo.controller;

import java.util.List;



import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;



import com.jangbogo.domain.member.entity.Member;
import com.jangbogo.advice.MemberNotFoundException;
import com.jangbogo.domain.Product;

import com.jangbogo.dto.ProductRequestDto;
import com.jangbogo.repository.MemberRepository;

import com.jangbogo.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@RequiredArgsConstructor
@RestController
public class ProductRestController {
	private final ProductService productService;

//	private final ProductRepository productRepository;
//	
//	//관심상품 전체 조회
//	@GetMapping("/api/products")
//	public List<Product> getProducts(){
//		return productRepository.findAll();
//	}
//	
//	//관심상품 등록
//	@PostMapping("/api/products")
//	public Product saveProduct(@RequestBody ProductRequestDto requestDto) {
//		Product product = new Product(requestDto);
//		productRepository.save(product);
//		return product;
//	}
//	
//	//관심가격 변경
//	@PutMapping("/api/products/{id}")
//	public Long updateProduct(@PathVariable Long id, @RequestBody ProductMypriceRequestDto requestDto) {
//		return productService.update(id, requestDto);
//	}
//	

	private final MemberRepository memberRepository;

	//관심상품 전체 조회
	@GetMapping("/api/products")
		public List<Product> getProductList(@AuthenticationPrincipal Member user){
		Member siteUser = memberRepository.findByName(user.getName())
                .orElseThrow(MemberNotFoundException::new);
	    return productService.getFavList(user);
	}
	
	//관심상품 등록


	@PostMapping("/api/products")
	public ResponseEntity<?> saveFavList(@Valid @RequestBody ProductRequestDto requestDto) {
		System.out.println("====================================="+requestDto);	
		Member user = getPrincipal();
		System.out.println("====================================="+user);
	    ProductRequestDto products = productService.saveProduct(user, requestDto);

	    if(products ==null)
	    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    return new ResponseEntity<>(products, HttpStatus.OK);
	}
	
	//관심상품 삭제
	@DeleteMapping("/api/products/{productId}")
	public void deleteFavList(@AuthenticationPrincipal Member member, @PathVariable String productId) {
        Member user = memberRepository.findByName(member.getName())
                .orElseThrow(MemberNotFoundException::new);
		productService.deleteProduct(user, productId);
	}
	
    private Member getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member user = memberRepository.findByName(authentication.getName())
                .orElseThrow(MemberNotFoundException::new);
      
        return user;
    }


}
