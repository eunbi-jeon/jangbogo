package com.jangbogo.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import com.jangbogo.domain.SCMember;
import com.jangbogo.domain.member.entity.Member;
import com.jangbogo.domain.Product;
import com.jangbogo.domain.Product_demo;
import com.jangbogo.dto.ProductRequestDto;
import com.jangbogo.repository.ProductRepository;
import com.jangbogo.repository.Product_demoRepository;
import com.jangbogo.service.ProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ProductRestController {
	private final ProductService productService;
	private final Product_demoRepository productdemoRepository;
	
	  @GetMapping("/api/show")
	    public List<Product_demo> getProductss() {
	        return productdemoRepository.findAll();
	    }
	  
    @PostMapping("/api/save")
    public Product_demo createProduct(@RequestBody ProductRequestDto requestDto) {
        Product_demo productd = new Product_demo(requestDto);
        productdemoRepository.save(productd);
        return productd;
    }
	
	//관심상품 전체 조회
	@GetMapping("/api/products")
	public List<ProductRequestDto> getMyItem(Member member){
		String email = member.getEmail();
		return productService.getProductList(member, email);
	}
	
	//관심상품 등록
	@PostMapping("/api/products")
	public Product saveMyItem(@AuthenticationPrincipal Member member, @RequestBody ProductRequestDto requestDto) {
		productService.saveProduct(member, requestDto);
		return productService.findByMemberId(member.getId());
	}
	
	//관심상품 삭제
	@DeleteMapping("/api/products/{productId}")
	public void deleteMyItem(@AuthenticationPrincipal Member member, @PathVariable Integer productId) {
		productService.deleteProduct(member, productId);
	}
	


}
