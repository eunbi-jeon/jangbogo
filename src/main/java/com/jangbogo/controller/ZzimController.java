package com.jangbogo.controller;

import java.util.List;

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
    
    //조회
    @GetMapping("/api/products")
    public ResponseEntity<?> zzimList(@CurrentUser UserPrincipal currentUser) {
        
    	Member user = memberRepository.findByEmail(currentUser.getEmail()).orElse(null); 
    	List<Product> product = productRepository.findAll();
//    	Zzim zzim = zzimRepository.findByUserEmail(user.getEmail());
//    	
//        List<Product> productList = zzimService.viewZzim(zzim);

        return ResponseEntity.ok(product);
    }

    @PostMapping("/api/products")
    public ResponseEntity<?> addProductToZzim(@CurrentUser UserPrincipal currentUser, 
    									@RequestBody ProductRequestDto productDto, Integer count) {

    	Member user = memberRepository.findByEmail(currentUser.getEmail()).orElse(null);

        zzimService.saveProducts(user, productDto, count);


        return ResponseEntity.ok(productDto);
    }

    @DeleteMapping("api/products/{prodId}")
    public ResponseEntity<?> removeProductFromFavList(@CurrentUser UserPrincipal currentUser, @PathVariable Product product) {
    	Member user = memberRepository.findByEmail(currentUser.getEmail()).orElse(null); 
    	Zzim zzim = zzimRepository.findByUserEmail(user.getEmail());
        
    	zzimService.deleteProduct(product.getId());
    	zzim.setCount(zzim.getCount()-1);

        return ResponseEntity.ok().build();
    }
}

