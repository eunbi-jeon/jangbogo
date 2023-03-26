package com.jangbogo;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Duration;
import java.time.LocalDate;

public class PriceData {
    public static void main(String[] args) {
    	
    	/*
    	// 실행 시 시간으로 설정
    	String today = LocalDate.now().toString();
    	System.out.println(today);
    	*/
    	
        String url = "http://www.kamis.or.kr/service/price/xml.do";
        String action = "dailyPriceByCategoryList";
        String certKey = "1e5a1d54-9b74-4b76-858d-bb13e17176b2";
        String certId = "22.euneu@gmail.com";
        String returnType = "json";
        String productClsCode = "01";
        String itemCategoryCode = "100";
        String countryCode = "1101";
        String regday = "2023-03-24" ; // today 변수 넣기 // 주말 날짜 오류 발생
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
            String result = response.getBody();
            System.out.println(result);
        } else {
            System.out.println("Request failed with status code: " + response.getStatusCodeValue());
        }
    }
}