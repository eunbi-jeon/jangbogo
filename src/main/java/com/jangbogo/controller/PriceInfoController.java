package com.jangbogo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;
import com.jangbogo.domain.PriceInfo;
import com.jangbogo.repository.PriceInfoRepository;
import com.jangbogo.service.PriceInfoService;
import com.jangbogo.util.PriceInfoJsonParser;

import lombok.AllArgsConstructor;



@CrossOrigin(origins = "http://localhost:3000")
@RestController
@AllArgsConstructor
public class PriceInfoController { 

	@Autowired
	private PriceInfoJsonParser priceInfoJsonParser; 

	@Autowired
	private PriceInfoRepository priceInfoRepository;

	@Autowired
	private PriceInfoService priceInfoService;

   
    
    @PostMapping("/")
    public void getPrice() throws JsonMappingException, JsonProcessingException{ 
    	
    	// Open Api data DB에 저장   
    	List<PriceInfo> priceList = priceInfoJsonParser.parsePriceInfo();
    	  priceInfoRepository.saveAll(priceList);
        
    	// 저장된 DB 반환 
	//	return priceInfoService.getPriceInfoList();
    }

  
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String savePriceInfo() throws JsonProcessingException {
        Gson gson = new Gson();
        String jsonString = gson.toJson(priceInfoService.getPriceInfoList());
        System.err.println(jsonString);
        return jsonString;
    }
    
    
}