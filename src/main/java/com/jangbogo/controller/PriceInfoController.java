package com.jangbogo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
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


    @Scheduled(cron = "0 0 17 * * ?")
    @PostMapping("/")
    public void getPrice() throws JsonMappingException, JsonProcessingException{

        List<PriceInfo> priceList = priceInfoJsonParser.parsePriceInfo();

        if (priceInfoRepository.count() == 0) {
            // DB에 저장된 데이터가 없는 경우
            priceInfoRepository.saveAll(priceList);
        } else {
            // DB에 저장된 데이터가 있는 경우 중복 체크 후 저장
            for (PriceInfo priceInfo : priceList) {
                String regDay = priceInfoJsonParser.getStartDate();
                List<PriceInfo> existingPriceInfos = priceInfoRepository.findByRegDay(regDay);

                if (existingPriceInfos.isEmpty()) {
                    // 새로운 데이터 저장
                    priceInfoRepository.save(priceInfo);
                }
            }
        }

    }



    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String jsonPriceInfo() throws JsonProcessingException {
        Gson gson = new Gson();
        String jsonString = gson.toJson(priceInfoService.getPriceInfoList());
        System.err.println(jsonString);
        return jsonString;
    }
    
    
}