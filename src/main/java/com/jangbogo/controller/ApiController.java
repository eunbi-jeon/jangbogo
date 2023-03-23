package com.jangbogo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jangbogo.dto.ApiRequestDTO;
import com.jangbogo.dto.ApiResponseDTO;
import com.jangbogo.service.ApiService;

@RestController
public class ApiController {
    private final ApiService apiService;

    @Autowired
    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }


    @GetMapping("/price")
    public ResponseEntity<ApiResponseDTO> getPriceList(ApiRequestDTO requestDto) {
        ApiResponseDTO responseDto = apiService.getPriceList(requestDto);
        return ResponseEntity.ok(responseDto);
    }

}
