package com.jangbogo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

<<<<<<< HEAD
import com.jangbogo.domain.Product;
=======
import com.jangbogo.domain.Product.Zzim;
import com.jangbogo.domain.Product.Product;
>>>>>>> be985e41549ba282b5d80546d617aeb64b2a5333
import com.jangbogo.domain.member.entity.Member;
import com.jangbogo.dto.ProductRequestDto;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

<<<<<<< HEAD
	Product findByUserAndProductId(Member user, String productId);
	List<Product> findByProductId(String productId);

	Product findByUser(Member member);
=======
	Product findByZzimIdAndProductId(Long zzimId, String productId);
	
	List<Product> findByProductId(String productId);

	Product findByZzimId(Long zzimId);
>>>>>>> be985e41549ba282b5d80546d617aeb64b2a5333


}
