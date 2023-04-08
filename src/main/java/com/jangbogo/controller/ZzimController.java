package com.jangbogo.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jangbogo.config.security.token.CurrentUser;
import com.jangbogo.config.security.token.UserPrincipal;
import com.jangbogo.domain.member.entity.Member;
import com.jangbogo.domain.product.Product;
import com.jangbogo.domain.product.Zzim;
import com.jangbogo.dto.ProductRequestDto;
import com.jangbogo.repository.MemberRepository;
import com.jangbogo.repository.ProductRepository;
import com.jangbogo.repository.ZzimRepository;
import com.jangbogo.service.ZzimService;


import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("http://localhost:3000")
public class ZzimController {


	private final ProductRepository productRepository;
    private final ZzimService zzimService;
    private final MemberRepository memberRepository;
    private final ZzimRepository zzimRepository;
    private final EntityManager entityManager;
    //조회
    @GetMapping("/api/products")
    public ResponseEntity<?> zzimList(@CurrentUser UserPrincipal currentUser) {
        
    	Member user = memberRepository.findByEmail(currentUser.getEmail()).orElse(null); 
    	Zzim zzim = zzimRepository.findByUserEmail(user.getEmail());
    	List<Product> products = zzimService.viewZzim(zzim);
    	   
    	 return ResponseEntity.ok(products);

    }

    @PostMapping("/api/products")
    public ResponseEntity<?> addProductToZzim(@CurrentUser UserPrincipal currentUser, 
    									@RequestBody ProductRequestDto productDto, Integer count) {

    	Member user = memberRepository.findByEmail(currentUser.getEmail()).orElse(null);

    	
        zzimService.saveProducts(user, productDto, count);
        return ResponseEntity.ok(productDto);
    }

    @DeleteMapping("api/products/{prodId}")
    public ResponseEntity<?> removeProductFromFavList(@CurrentUser UserPrincipal currentUser, @PathVariable("prodId")  Long prodId) {
    	  Member user = memberRepository.findByEmail(currentUser.getEmail()).orElse(null); 
    	    Zzim zzim = zzimRepository.findByUserIdAndProducts_Id(user.getId(), prodId);
    	 
    	     zzimService.deleteProduct(prodId);
        return ResponseEntity.ok().build();
    }
    
}

