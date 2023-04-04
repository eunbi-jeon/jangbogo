package com.jangbogo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    public String getItemcode() {
		return itemcode;
	}
	public void setItemcode(String itemcode) {
		this.itemcode = itemcode;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getDay1() {
		return day1;
	}
	public void setDay1(String day1) {
		this.day1 = day1;
	}
	public String getDpr1() {
		return dpr1;
	}
	public void setDpr1(String dpr1) {
		this.dpr1 = dpr1;
	}
	public String getDay2() {
		return day2;
	}
	public void setDay2(String day2) {
		this.day2 = day2;
	}
	public String getDpr2() {
		return dpr2;
	}
	public void setDpr2(String dpr2) {
		this.dpr2 = dpr2;
	}
	public String getDay3() {
		return day3;
	}
	public void setDay3(String day3) {
		this.day3 = day3;
	}
	public String getDpr3() {
		return dpr3;
	}
	public void setDpr3(String dpr3) {
		this.dpr3 = dpr3;
	}
	private String dpr3;

}
