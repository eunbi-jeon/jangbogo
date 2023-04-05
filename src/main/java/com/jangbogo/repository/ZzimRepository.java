package com.jangbogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jangbogo.domain.Product.Zzim;


public interface ZzimRepository extends JpaRepository<Zzim, Long>{

	Zzim findByUserEmail(String email);

}
