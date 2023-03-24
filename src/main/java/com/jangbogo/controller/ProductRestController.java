package com.jangbogo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import com.jangbogo.domain.Product;
import com.jangbogo.dto.ProductMypriceRequestDto;
import com.jangbogo.dto.ProductRequestDto;
import com.jangbogo.repository.ProductRepository;
import com.jangbogo.service.ProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ProductRestController {
	private final ProductService productService;
	private final ProductRepository productRepository;
	
	//관심상품 전체 조회
	@GetMapping("/api/products")
	public List<Product> getProducts(){
		return productRepository.findAll();
	}
	
	//관심상품 등록
	@PostMapping("/api/products")
	public Product saveProduct(@RequestBody ProductRequestDto requestDto) {
		Product product = new Product(requestDto);
		productRepository.save(product);
		return product;
	}
	
	//관심가격 변경
	@PutMapping("/api/products/{id}")
	public Long updateProduct(@PathVariable Long id, @RequestBody ProductMypriceRequestDto requestDto) {
		return productService.update(id, requestDto);
	}
	

}
