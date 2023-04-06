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
    private String category2;
    private String category3;
    private String category4;

    
    public ItemDto(JSONObject itemJson) {
    	this.productId=itemJson.getString("productId");
<<<<<<< HEAD

    	this.title = itemJson.getString("title");
    	this.link = itemJson.getString("link");
        this.image = itemJson.getString("image");

=======
    	this.title = itemJson.getString("title");
    	this.link = itemJson.getString("link");
        this.image = itemJson.getString("image");
        this.lprice = itemJson.getInt("lprice");
>>>>>>> be985e41549ba282b5d80546d617aeb64b2a5333
        this.mallName = itemJson.getString("mallName");
        this.category1=itemJson.getString("category1");
        this.category2=itemJson.getString("category2");
        this.category3=itemJson.getString("category3");
        this.category4=itemJson.getString("category4");
        

    }
}
