package com.jangbogo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jangbogo.domain.PriceInfo;


@Repository
public interface PriceInfoRepository extends JpaRepository<PriceInfo, Long> { 
	List<PriceInfo> findAll();
    List<PriceInfo> findByRegDay(String date);

    //아이템 이름으로 검색
    @Query(value = "SELECT * FROM PRICE_INFO WHERE item_name LIKE %:keyword% OR kind_name LIKE %:keyword%", nativeQuery = true)
    List<PriceInfo> findByKeyword(@Param("keyword") String keyword);
    
}

