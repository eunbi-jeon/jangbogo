package com.jangbogo.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString 
public class PriceInfoDTO {

  
    private Long id;
    
    private String itemName; 

    private String itemCode;

    private String kindName;

    private String unit;

    private String day1;
    
    private String dpr1; 
    
    private String day2;
    
    private String dpr2;

    private String day3;

    private String dpr3;

    private String regDay; 
    
    private String rank;

	
    
}