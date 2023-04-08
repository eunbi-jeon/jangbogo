package com.jangbogo.dto;

import org.json.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import lombok.ToString;


@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor

@ToString
public class ItemDto {
	private String productId;
	private String title;
    private String link;
    private String image;
    private Integer lprice;
    private String mallName;
    private String category1;
    private String sort;

    
    public ItemDto(JSONObject itemJson) {
    	this.productId=itemJson.getString("productId");
    	this.title = itemJson.getString("title");
    	this.link = itemJson.getString("link");
        this.image = itemJson.getString("image");
        this.lprice = itemJson.getInt("lprice");
        this.mallName = itemJson.getString("mallName");
        this.category1=itemJson.getString("category1");

    }
}
