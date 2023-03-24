package com.jangbogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jangbogo.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	
	
}
