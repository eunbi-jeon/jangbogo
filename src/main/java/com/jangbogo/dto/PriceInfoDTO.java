package com.jangbogo.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter; 

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor 
@Entity 
@Table(name = "price_info")
public class PriceInfoDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_code")
    private String itemCode;

    @Column(name = "kind_name")
    private String kindName;

    @Column(name = "unit")
    private String unit;

    @Column(name = "day1")
    private String day1;

    @Column(name = "dpr1")
    private String dpr1;

    @Column(name = "day3")
    private String day3;

    @Column(name = "dpr3") 
    private String dpr3;

    @Column(name = "reg_day") 
    private String regDay; 
    
    @Column(name = "rank") 
    private String rank;

	public PriceInfoDTO(JSONObject itemNode, String startDate) {
		this.itemName = itemNode.getString("item_name");
	    this.itemCode = itemNode.getString("item_code");
	    this.kindName = itemNode.getString("kind_name"); 
	    this.day1 = itemNode.getString("day1");
	    this.dpr1 = itemNode.getString("dpr1");
	    this.day3 = itemNode.getString("day3");
	    this.dpr3 = itemNode.getString("dpr3");
	    this.unit = itemNode.getString("unit");
	    this.rank = itemNode.getString("rank"); 
	    this.regDay = startDate; 
		
	}
	
	public PriceInfoDTO() {}



	
	

	



	
	
	



	

    
}