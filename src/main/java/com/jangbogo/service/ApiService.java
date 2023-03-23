package com.jangbogo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jangbogo.dto.ApiRequestDTO;
import com.jangbogo.dto.ApiResponseDTO;

@Service
public class ApiService {
    public ApiResponseDTO getPriceList(ApiRequestDTO requestDto) {
        String url = "http://www.kamis.or.kr/service/price/xml.do?action=dailyPriceByCategoryList"
                + "&p_cert_key=" + requestDto.getP_cert_key()
                + "&p_cert_id=" + requestDto.getP_cert_id()
                + "&p_returntype=" + requestDto.getP_returntype()
                + "&p_product_cls_code=" + requestDto.getP_product_cls_code()
                + "&p_itemcategorycode=" + requestDto.getP_itemcategorycode()
                + "&p_country_code=" + requestDto.getP_country_code()
                + "&p_regday=" + requestDto.getP_regday()
                + "&p_itemcode=" + requestDto.getP_itemcode()
                + "&p_convert_kg_yn=" + requestDto.getP_convert_kg_yn();

        RestTemplate restTemplate = new RestTemplate();
        ApiResponseDTO responseDto = restTemplate.getForObject(url, ApiResponseDTO.class);

        return responseDto;
    }
}
