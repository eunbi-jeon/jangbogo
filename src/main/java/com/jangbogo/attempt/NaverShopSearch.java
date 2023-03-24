package com.jangbogo.attempt;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.jangbogo.dto.ItemDto;

@Component
public class NaverShopSearch {

	public String search(String query) {
		RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", "ZbxOPpeRfxe__sSH8zBF");
        headers.add("X-Naver-Client-Secret", "39TSuFgGEc");
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity = rest.exchange("https://openapi.naver.com/v1/search/shop.json?query=" + query, HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        String response = responseEntity.getBody();
        System.out.println("Response status: " + status);
        System.out.println(response);

        return response;
	};
	
	 // 검색된 상품 목록 데이터를 DTO로 변환
		public List<ItemDto> fromJSONtoItems(String result){
			JSONObject rslt = new JSONObject(result);
			JSONArray items = rslt.getJSONArray("items");
			List<ItemDto> itmList = new ArrayList<>();
		
			for (int i=0; i<items.length();i++) {
				JSONObject itemJson = items.getJSONObject(i);
				System.out.println(itemJson);
				ItemDto itemDto = new ItemDto(itemJson);
				itmList.add(itemDto);
			}
			return itmList;
	};

	

}
