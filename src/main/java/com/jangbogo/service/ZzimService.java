package com.jangbogo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import com.jangbogo.domain.member.entity.Member;
import com.jangbogo.domain.product.Product;
import com.jangbogo.domain.product.Zzim;
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

	    if (zzim == null) {

	        zzim = Zzim.creatZzim(user);
	        zzimRepository.save(zzim);
	    }

        Product product = productRepository.findByZzimIdAndProductId(zzim.getId(), req.getProductId());
        if(product==null) {
	        productRepository.save(buildProductreq(req, zzim));	
        	zzim.setCount(zzim.getCount() + 1);
        }else {
        	product.getCreateAt().toString();
        }
	     
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
	            .build();
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
	    @Transactional
	    public void deleteProduct(Long productId) {
	        Product product = productRepository.findById(productId)
	                .orElseThrow(() -> new NotFoundException("Product not found"));
	        Zzim zzim = product.getZzim();
	        zzim.getProducts().remove(product);
	        zzim.addCount(-1);
	        product.setZzim(null);
	        productRepository.delete(product);
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
