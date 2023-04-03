package com.jangbogo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jangbogo.domain.PriceInfo;


@Repository
public interface PriceInfoRepository extends JpaRepository<PriceInfo, Long> { 
	List<PriceInfo> findAll();
    List<PriceInfo> findByRegDay(String date);  
    
}

