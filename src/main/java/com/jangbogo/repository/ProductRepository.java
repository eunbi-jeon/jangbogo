package com.jangbogo.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jangbogo.domain.Product;
import com.jangbogo.domain.member.entity.Member;
import com.jangbogo.dto.ProductRequestDto;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	Product findByMemberIdAndId(Long memberId, Integer productId);
	List<ProductRequestDto> findByProductId(Integer productId);
	Product findByMemberId(Long id);


}
