package com.jangbogo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.jangbogo.domain.SCMember;
import com.jangbogo.domain.member.entity.Member;
import com.jangbogo.domain.Product;

import com.jangbogo.dto.ProductRequestDto;
import com.jangbogo.repository.MemberRepository;
import com.jangbogo.repository.ProductRepository;
import com.jangbogo.repository.SCMemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class ProductService {

	private final ProductRepository productRepository;
	private final MemberRepository memberRepository;

	//조회


	public List<ProductRequestDto> getProductList(Member member, String email){
		List<ProductRequestDto> productList = new ArrayList<>();
		
		Product product = productRepository.findByMemberId(member.getId());
		if(product==null) {
			return productList;
		}
		productList = productRepository.findByProductIdN(product.getProductId());
		return productList; 
	}
	
	
	// 저장
	
	public void saveProduct(Member member, ProductRequestDto requestDto) {
	    Product product = productRepository.findByMemberId(member.getId());
	    if(product == null) {
	        product = Product.createList(member);
	        product.setProductId(requestDto.getProductId());
	        product.setTitle(requestDto.getTitle());
	        product.setImage(requestDto.getImage());
	        product.setLink(requestDto.getLink());
	        product.setLprice(requestDto.getLprice());
	        product.setMallName(requestDto.getMallName());
	        
	        productRepository.save(product);
	    }else {
	    	product.addCount(1);
	    	productRepository.save(product);
	    }
	    
	};
	
	
	public void deleteProduct(Member member, Integer productIdN) {
	    Product product = productRepository.findByMemberIdAndId(member.getId(), productIdN);
	    if (product != null) {
	        productRepository.delete(product);
	        
	    }
	}
	
	public Product findByMemberId(Long memberId) {
	    return productRepository.findByMemberId(memberId);
	}


	public void deleteProduct(Member member, Long productId) {
		// TODO Auto-generated method stub
		
	}



	
}
