//package com.jangbogo.controller;
//
//import java.security.Principal;
//import java.util.List;
//
//import javax.validation.Valid;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//import com.jangbogo.config.security.token.CurrentUser;
//import com.jangbogo.config.security.token.UserPrincipal;
//import com.jangbogo.domain.Product.Zzim;
//import com.jangbogo.domain.Product.Product;
//import com.jangbogo.domain.member.entity.Member;
//import com.jangbogo.dto.ProductRequestDto;
//import com.jangbogo.repository.ZzimRepository;
//import com.jangbogo.repository.MemberRepository;
//import com.jangbogo.service.ZzimService;
//import com.jangbogo.service.ProductService;
//import com.jangbogo.exeption.MemberNotFoundException;
//
//import lombok.RequiredArgsConstructor;
//
//
//
//@RequiredArgsConstructor
//@RestController
//@CrossOrigin("http://localhost:3000")
//public class ProductRestController {
//	private final ProductService productService;
//	private final MemberRepository memberRepository;
//    private final ZzimService favListService;
//
//
//	//관심상품 전체 조회
//    @GetMapping("/api/products")
//    public ResponseEntity<?> getFavList(@CurrentUser UserPrincipal currentUser){
//   
//    	Member user = memberRepository.findByEmail(currentUser.getEmail()).orElse(null); 
//    	
//    	Zzim favList = favListService.getFavList(user);
//    	List<Product> productList = favList.getProducts();
//    
//    	return ResponseEntity.ok(productList);
//    }
//	//관심상품 등록
//
//
//	@PostMapping("/api/products")
//	public ResponseEntity<?> saveFavList(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody ProductRequestDto requestDto) {
//	
//		Member user = memberRepository.findByEmail(currentUser.getEmail()).orElse(null); 
//		Zzim favList = favListService.getFavList(user);
//		ProductRequestDto product = productService.saveProduct(requestDto);
//
//	    if(product == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//	    return new ResponseEntity<>(product, HttpStatus.OK);
//	}
//	
//	//관심상품 삭제
//	@DeleteMapping("/api/products/{productId}")
//    public ResponseEntity<?> deleteFavList(@CurrentUser UserPrincipal currentUser, @PathVariable String productId) {
//        Member user = memberRepository.findByEmail(currentUser.getEmail()).orElse(null); 
//        Zzim favList = favListService.getFavList(user);
//        
//		productService.deleteProduct(user, productId);
//		
//        
//        if(favList == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        return new ResponseEntity<>(favList, HttpStatus.OK);
//	}
//	
//
////	private final ProductRepository productRepository;
////	
////	//관심상품 전체 조회
////	@GetMapping("/api/products")
////	public List<Product> getProducts(){
////		return productRepository.findAll();
////	}
////	
////	//관심상품 등록
////	@PostMapping("/api/products")
////	public Product saveProduct(@RequestBody ProductRequestDto requestDto) {
////		Product product = new Product(requestDto);
////		productRepository.save(product);
////		return product;
////	}
////	
////	//관심가격 변경
////	@PutMapping("/api/products/{id}")
////	public Long updateProduct(@PathVariable Long id, @RequestBody ProductMypriceRequestDto requestDto) {
////		return productService.update(id, requestDto);
////	}
////	
//}
