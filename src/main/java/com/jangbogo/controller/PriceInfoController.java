package com.jangbogo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jangbogo.service.PriceInfoService;

@RestController
public class PriceInfoController {

    @Autowired
    private PriceInfoService priceInfoService;
    
    @GetMapping("/priceinfo")
    public ResponseEntity<String> getPriceInfo() {
        // 서비스에서 JSON 데이터 가져오기
        String responseData = priceInfoService.getPriceInfo();
        
        System.out.println("출력");
        
        // ResponseEntity로 JSON 데이터 반환
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}

