package com.jangbogo.attempt;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jangbogo.domain.Product;
import com.jangbogo.dto.ItemDto;
import com.jangbogo.repository.ProductRepository;
import com.jangbogo.service.ProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class Scheduler {

	private final ProductRepository productRepository;
	private final ProductService productService;
	private final NaverShopSearch naverShopSearch;

	// 예약된 시간에 메서드 실행 - 매일 새벽 1시 0분 0초 일 때 실행
    @Scheduled(cron = "0 0 1 * * *") // cron - 초, 분, 시, 일, 월, 주 순서
    public void updatePrice() throws InterruptedException {
        System.out.println("가격 업데이트 실행");
        // 저장된 모든 관심상품을 조회
        List<Product> productList = productRepository.findAll();
        for (int i=0; i<productList.size(); i++) {
            // 1초에 한 상품 씩 조회 (Naver 제한)
            TimeUnit.SECONDS.sleep(1);
            // i 번째 관심 상품을 꺼낸다
            Product p = productList.get(i);
            // i 번째 관심 상품의 제목으로 검색을 실행
            String title = p.getTitle();
            String resultString = naverShopSearch.search(title);
            // i 번째 관심 상품의 검색 결과 목록 중에서 첫 번째 결과를 꺼낸다
            List<ItemDto> itemDtoList = naverShopSearch.fromJSONtoItems(resultString);
            ItemDto itemDto = itemDtoList.get(0);
            // i 번째 관심 상품 정보를 업데이트
            Long id = p.getId();
            productService.updateBySearch(id, itemDto);
        }
    }
}
