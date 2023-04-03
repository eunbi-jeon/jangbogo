package com.jangbogo.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jangbogo.domain.Product;
import com.jangbogo.domain.member.entity.Member;
import com.jangbogo.dto.ProductRequestDto;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	Product findByUserAndProductId(Member user, String productId);
	List<Product> findByProductId(String productId);
	Product findByUser(Member user);
	Product findByUser(String name);


}
