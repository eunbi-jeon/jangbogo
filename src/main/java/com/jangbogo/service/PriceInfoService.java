package com.jangbogo.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PriceInfoService {

    public String getPriceInfo() {
        // RestTemplate 객체 생성
        RestTemplate restTemplate = new RestTemplate();
        
        // HTTP 요청에 사용할 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        // API URL 설정
        String apiUrl = "http://www.kamis.or.kr/service/price/xml.do?action=dailyPriceByCategoryList&p_cert_key=1e5a1d54-9b74-4b76-858d-bb13e17176b2&p_cert_id=22.euneu@gmail.com&p_product_cls_code=01&p_returntype=json&p_country_code=1101&p_regday=2023-03-22&p_convert_kg_yn=Y";

        // RestTemplate으로 HTTP GET 요청 보내기
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);
        
        // 응답 데이터 가져오기
        String responseData = responseEntity.getBody();
        
        // JSON 데이터 변환하여 반환
        return responseData;
    }
}

