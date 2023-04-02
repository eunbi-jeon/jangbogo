package com.jangbogo.util;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jangbogo.dto.PriceInfoDTO;

@Component 
public class PriceInfoJsonParser {
	
    
    public String getStartDate() {
        LocalDateTime now = LocalDateTime.now();
        if (now.getHour() < 17) {
            now = now.minusDays(1);
        }
        LocalDate date = now.toLocalDate();
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
            date = date.minusDays(1);
        } else if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            date = date.minusDays(2);
        }
        return date.toString();
    } 
    
    public List<PriceInfoDTO> parsePriceInfo() throws JsonMappingException, JsonProcessingException {             
        String startDate = getStartDate();                
        String url = "http://www.kamis.or.kr/service/price/xml.do";
        String action = "dailyPriceByCategoryList";
        String certKey = "1e5a1d54-9b74-4b76-858d-bb13e17176b2";
        String certId = "22.euneu@gmail.com";
        String returnType = "json";
        String productClsCode = "01";
        String countryCode = "1101";
        String convertKgYn = "Y";
        
        RestTemplate restTemplate = new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(10))
                .build();


        ObjectMapper objectMapper = new ObjectMapper();
        
        List<PriceInfoDTO> priceList = new ArrayList<>();
        
        String[] itemCategoryCodes = {"100", "200", "300", "400", "500", "600"}; 
        
        for (String itemCategoryCode : itemCategoryCodes) {
            String regday = startDate; 
            String itemCategoryCode1 = itemCategoryCode; 
            
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                    .queryParam("action", action)
                    .queryParam("p_cert_key", certKey)
                    .queryParam("p_cert_id", certId)
                    .queryParam("p_returntype", returnType)
                    .queryParam("p_product_cls_code", productClsCode)
                    .queryParam("p_item_category_code", itemCategoryCode1) 
                    .queryParam("p_country_code", countryCode)
                    .queryParam("p_regday", regday)
                    .queryParam("p_convert_kg_yn", convertKgYn);

            String responseString = restTemplate.getForObject(builder.toUriString(), String.class);
            
            
            JsonNode rootNode = objectMapper.readTree(responseString);
            JsonNode itemNode = rootNode.path("data").path("item");

            for (JsonNode item : itemNode) {
                JSONObject itemJson = new JSONObject(item.toString());
                PriceInfoDTO priceInfoDTO = new PriceInfoDTO(itemJson, startDate);             
                priceList.add(priceInfoDTO);
            }
        }
        
      
        
        return priceList;
    }
    
 



}