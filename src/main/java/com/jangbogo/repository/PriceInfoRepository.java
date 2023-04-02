package com.jangbogo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jangbogo.dto.PriceInfoDTO;

@Repository
public interface PriceInfoRepository extends JpaRepository<PriceInfoDTO, Long> { 
	List<PriceInfoDTO> findAll();
    List<PriceInfoDTO> findByRegDay(String date);  
    
}

