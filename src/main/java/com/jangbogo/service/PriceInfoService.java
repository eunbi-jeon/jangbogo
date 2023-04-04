package com.jangbogo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jangbogo.domain.PriceInfo;
import com.jangbogo.repository.PriceInfoRepository;
import com.jangbogo.util.PriceInfoJsonParser;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PriceInfoService {

	@Autowired
	private PriceInfoRepository priceInfoRepository;
	@Autowired
	private PriceInfoJsonParser priceInfoJsonParser;
    
  
    public List<PriceInfo> getPriceInfoList() {
    	String date = priceInfoJsonParser.getStartDate();
        return priceInfoRepository.findByRegDay(date);
    }

}
