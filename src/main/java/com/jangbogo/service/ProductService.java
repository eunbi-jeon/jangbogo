package com.jangbogo.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;


import com.jangbogo.domain.Product;
import com.jangbogo.dto.ItemDto;
import com.jangbogo.dto.ProductMypriceRequestDto;
import com.jangbogo.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {

	private final ProductRepository productRepository;
	
	@Transactional
	public Long update(Long id, ProductMypriceRequestDto requestDto) {
		Product product = productRepository.findById(id).orElseThrow(
				()-> new NullPointerException("해당 아이디가 존재하지 않습니다."));
		
		product.update(requestDto); //관심가격 변경
		return id;
	}
	
	@Transactional
	public Long updateBySearch (Long id, ItemDto itemDto) {
		Product product = productRepository.findById(id).orElseThrow(
				()-> new NullPointerException("해당 아이디가 존재하지 않습니다."));
		
		product.updateByItemDto(itemDto);//예약시간에 가격 변경
		return id;
	}
	
}
