//package com.jangbogo.service;
//
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//
//import javax.transaction.Transactional;
//
//import org.springframework.stereotype.Service;
//
//import com.jangbogo.domain.Product.Zzim;
//import com.jangbogo.domain.Product.Product;
////<<<<<<< HEAD
////import com.jangbogo.domain.Product;
////import com.jangbogo.dto.ItemDto;
////import com.jangbogo.dto.ProductMypriceRequestDto;
////import com.jangbogo.repository.ProductRepository;
////
////import lombok.RequiredArgsConstructor;
////
////@RequiredArgsConstructor
////@Service
////public class ProductService {
////
////	private final ProductRepository productRepository;
////	
////	@Transactional
////	public Long update(Long id, ProductMypriceRequestDto requestDto) {
////		Product product = productRepository.findById(id).orElseThrow(
////				()-> new NullPointerException("해당 아이디가 존재하지 않습니다."));
////		
////		product.update(requestDto); //관심가격 변경
////		return id;
////	}
////	
////	@Transactional
////	public Long updateBySearch (Long id, ItemDto itemDto) {
////		Product product = productRepository.findById(id).orElseThrow(
////				()-> new NullPointerException("해당 아이디가 존재하지 않습니다."));
////		
////		product.updateByItemDto(itemDto);//예약시간에 가격 변경
////		return id;
////	}
////=======
//import com.jangbogo.domain.member.entity.Member;
//import com.jangbogo.dto.ProductRequestDto;
//import com.jangbogo.repository.ZzimRepository;
//import com.jangbogo.repository.MemberRepository;
//import com.jangbogo.repository.ProductRepository;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//@Slf4j
//@RequiredArgsConstructor
//@Service
//@Transactional
//public class ProductService {
//
//
//	private final ProductRepository productRepository;
//	private final MemberRepository memberRepository;
//
//
//	
//    
//	
//	
//	//fav 품목 조회
//	public List<Product> showFavList(Long favListId){
//		
//		List<Product> productList = new ArrayList<>();
//		
//		Product product = productRepository.findByfavListId(favListId);
//		
//		productList = productRepository.findByProductId(product.getProductId());
//		return productList; 
//	}
//	
//	
//	// 저장 product->list 
//	public ProductRequestDto saveProduct(ProductRequestDto req) {
//	  
//
//	    Product product = productRepository.save(buildProductreq(req));
//	    product.getProductId();
//	    return null;
//	}
//
//	private Product buildProductreq(ProductRequestDto req) {
//		
//		return Product.builder()
//				.productId(req.getProductId())
//				.title(req.getTitle())
//				.image(req.getImage())
//				.link(req.getLink())
//				.lprice(req.getLprice())
//				.mallName(req.getMallName())
//				.build()
//				;
//	}
//
//	
//	
//
//	//삭제
//	@Transactional
//	public void deleteProduct(Member user, String productId) {
//		Zzim favList = createFavList(user);
//	    favList.removeProduct(productId);
//	    
//	    favListRepository.save(favList);
//	    
//	}
//	
//
//}
