package com.jangbogo.controller;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;

import org.apache.http.HttpStatus;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;



@CrossOrigin("http://localhost:3000")
@RestController
public class PriceController {

    @GetMapping("/api/price")
    public ResponseEntity<JsonNode> getPrice() throws JsonMappingException, JsonProcessingException  {

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
        

        // 연결 성공 
        if (response.getStatusCode().is2xxSuccessful()) {
        	
        	
        	// 데이터 파싱 
        	String jsondata = response.getBody();
        	ObjectMapper objectMapper = new ObjectMapper();
        	JsonNode rootNode = objectMapper.readTree(jsondata);
        	JsonNode itemNode = rootNode.path("data").path("item");
        	

        	System.out.println(itemNode);
        	
        	/*
        	for (JsonNode item : itemNode) {
        	    System.out.println("item_name: " + item.get("item_name").asText());
        	    System.out.println("item_code: " + item.get("item_code").asText());
        	    System.out.println("kind_name: " + item.get("kind_name").asText());
        	    System.out.println("kind_code: " + item.get("kind_code").asText());
        	    System.out.println("rank: " + item.get("rank").asText());
        	    System.out.println("rank_code: " + item.get("rank_code").asText());
        	    System.out.println("unit: " + item.get("unit").asText());
        	    System.out.println("day1: " + item.get("day1").asText());
        	    System.out.println("dpr1: " + item.get("dpr1").asText());
        	    System.out.println("day2: " + item.get("day2").asText());
        	    System.out.println("dpr2: " + item.get("dpr2").asText());
        	    System.out.println("day3: " + item.get("day3").asText());
        	    System.out.println("dpr3: " + item.get("dpr3").asText());
        	    System.out.println("day4: " + item.get("day4").asText());
        	    System.out.println("dpr4: " + item.get("dpr4").asText());
        	    System.out.println("day5: " + item.get("day5").asText());
        	    System.out.println("dpr5: " + item.get("dpr5").asText());
        	    System.out.println("day6: " + item.get("day6").asText());
        	    System.out.println("dpr6: " + item.get("dpr6").asText());
        	    System.out.println("day7: " + item.get("day7").asText());
        	    System.out.println("dpr7: " + item.get("dpr7").asText());
        	}
        	*/


            
        	return ResponseEntity.ok(itemNode);

            
        // 연결 실패	 
        } else {
        	return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(null);
        }
        
        
    }
    
    
    
}