package com.jangbogo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jangbogo.domain.Product_demo;


@Repository
public interface Product_demoRepository extends JpaRepository<Product_demo, Long>{


}
