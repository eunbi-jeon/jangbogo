package com.jangbogo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jangbogo.domain.member.entity.Member;
import com.jangbogo.domain.product.Product;
import com.jangbogo.domain.product.Zzim;
import com.jangbogo.dto.ProductRequestDto;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	Product findByZzimIdAndProductId(Long zzimId, String productId);
	
	List<Product> findByProductId(String productId);

	Product findByZzimId(Long zzimId);

	List<Product> findByZzim(Zzim zzim);




}
