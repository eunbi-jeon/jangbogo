package com.jangbogo.dto;

import lombok.Data;

@Data
public class PriceDto {
	
    private String itemcode;
    private String item_name;
    private String unit;
    private String day1;
    private String dpr1;
    private String day2;
    private String dpr2;
    private String day3;
    private String dpr3;

}
