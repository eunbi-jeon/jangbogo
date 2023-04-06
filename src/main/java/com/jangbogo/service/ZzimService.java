package com.jangbogo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.jangbogo.domain.Product.Zzim;
import com.jangbogo.domain.Product.Product;
import com.jangbogo.domain.member.entity.Member;
import com.jangbogo.dto.ProductRequestDto;
import com.jangbogo.repository.ZzimRepository;
import com.jangbogo.repository.MemberRepository;
import com.jangbogo.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ZzimService {
	
	private final ZzimRepository zzimRepository ;
	private final ProductRepository productRepository ;
	private final MemberRepository memberRepository ;
	

	//찜 list 생성
	public void createZzim(Member user) {
		Zzim zzim = Zzim.creatZzim(user);
	      zzimRepository.save(zzim);
	}
           
	
	//prod 저장
	public void saveProducts(Member user, ProductRequestDto req, Integer count) {
	    Zzim zzim = zzimRepository.findByUserEmail(user.getEmail());

		log.info("찜 처리 시작");
	    if (zzim == null) {
			log.info("찜 생성");
	    	zzim = Zzim.creatZzim(user);
	    	zzimRepository.save(zzim);
			log.info("찜 저장완료");
	    }

<<<<<<< HEAD
		    productRepository.save(buildProductreq(req));
	    	zzim.setCount(zzim.getCount()+1);
	    }else {
	    	product.addCount(count);
	    }
=======
		log.info("찜 방 번호로 상품 찾기");
	    Product product = productRepository.findByZzimId(zzim.getId());

		try {

			if (product == null) {
				log.info("상품 저장");
				productRepository.save(buildProductreq(req,zzim));
				zzim.setCount(zzim.getCount() + 1);
				log.info("상품 카운트 증가");
			} else {
				product.addCount(count);
				log.info("카운트 추가");
			}

		} catch (Exception e){
			e.printStackTrace();
		}
>>>>>>> be985e41549ba282b5d80546d617aeb64b2a5333
	}

		private Product buildProductreq(ProductRequestDto req, Zzim zzim) {

				return Product.builder()
						.productId(req.getProductId())
						.title(req.getTitle())
						.image(req.getImage())
						.link(req.getLink())
						.lprice(req.getLprice())
						.mallName(req.getMallName())
						.zzim(zzim)
						.build()
						;
			}
		
		//찜 리스트 조회
		public List<Product> viewZzim(Zzim zzim){
			List<Product> products = productRepository.findAll();
			
			List<Product> items = new ArrayList<>();
			
			for(Product product : products) {
				if(product.getZzim().getId()==zzim.getId()) {
					items.add(product);
				}
			}
			return items;
		}
		
		//찜 product 삭제
		public void deleteProduct(Long id) {
			productRepository.deleteById(id);
		}
		
		//찜 전체 삭제
		public void deleteAll(Long id) {
			List<Product> products = productRepository.findAll();
			
			for(Product product: products) {
				if(product.getZzim().getUser().getId()==id) {
					product.getZzim().setCount(product.getZzim().getCount()-1);
					productRepository.deleteById(product.getId());
					}
				}
		}

}
