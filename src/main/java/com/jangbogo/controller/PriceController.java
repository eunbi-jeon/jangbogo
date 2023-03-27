package com.jangbogo.controller;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;

import org.apache.http.HttpStatus;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class PriceController {

    @GetMapping("/api/price")
    public ResponseEntity<String> getPrice() {

        // 실행 시 시간으로 데이터 불러옴 (주말인 경우 그 주의 금요일 정보 출력)
    	 LocalDate date = LocalDate.now();
         if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
             date = date.minusDays(1);
         } else if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
             date = date.minusDays(2);
         }
        String startDate = date.toString();

        String url = "http://www.kamis.or.kr/service/price/xml.do";
        String action = "dailyPriceByCategoryList";
        String certKey = "1e5a1d54-9b74-4b76-858d-bb13e17176b2";
        String certId = "22.euneu@gmail.com";
        String returnType = "json";
        String productClsCode = "01";
        String itemCategoryCode = "100";
        String countryCode = "1101";
        String regday = startDate ;
        String convertKgYn = "Y";

        RestTemplate restTemplate = new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(10))
                .build();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("action", action)
                .queryParam("p_cert_key", certKey)
                .queryParam("p_cert_id", certId)
                .queryParam("p_returntype", returnType)
                .queryParam("p_product_cls_code", productClsCode)
                .queryParam("p_item_category_code", itemCategoryCode)
                .queryParam("p_country_code", countryCode)
                .queryParam("p_regday", regday)
                .queryParam("p_convert_kg_yn", convertKgYn);

        ResponseEntity<String> response = restTemplate.getForEntity(builder.toUriString(), String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
        	
        	
        	
            return response;
            
        } else {
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body("Request failed");
        }
    }
    
    
    
}