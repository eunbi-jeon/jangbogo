package com.jangbogo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jangbogo.dto.PriceInfoDTO;
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

   
    
    @GetMapping("/")
    public List<PriceInfoDTO> getPrice() throws JsonMappingException, JsonProcessingException{ 
    	
    	// Open Api data DB에 저장   
    	List<PriceInfoDTO> priceList = priceInfoJsonParser.parsePriceInfo();
    	  priceInfoRepository.saveAll(priceList);
        
    	// 저장된 DB 반환 
        List<PriceInfoDTO> priceInfo = priceInfoService.getPriceInfoList();
        
        return priceInfo;  
    }
    
}