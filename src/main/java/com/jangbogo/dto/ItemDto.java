package com.jangbogo.dto;



import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    private String title;
    private String link;
    private String image;
    private Integer lprice;
    
    public ItemDto(JSONObject itemJson) {
    	this.title = itemJson.getString("title");
    	this.link = itemJson.getString("link");
        this.image = itemJson.getString("image");
        this.lprice = itemJson.getInt("lprice");
    }
}
