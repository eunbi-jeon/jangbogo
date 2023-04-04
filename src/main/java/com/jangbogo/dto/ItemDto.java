package com.jangbogo.dto;



import org.json.JSONObject;

<<<<<<< HEAD
=======
import com.jangbogo.domain.member.entity.Member;

>>>>>>> ed6d9544c8bb75292349dd76fd507a0d1827cbaf
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
<<<<<<< HEAD
=======
import lombok.ToString;
>>>>>>> ed6d9544c8bb75292349dd76fd507a0d1827cbaf

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
<<<<<<< HEAD
public class ItemDto {
    private String title;
    private String link;
    private String image;
    private Integer lprice;
    
    public ItemDto(JSONObject itemJson) {
=======
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
>>>>>>> ed6d9544c8bb75292349dd76fd507a0d1827cbaf
    	this.title = itemJson.getString("title");
    	this.link = itemJson.getString("link");
        this.image = itemJson.getString("image");
        this.lprice = itemJson.getInt("lprice");
<<<<<<< HEAD
=======
        this.mallName = itemJson.getString("mallName");
        this.category1=itemJson.getString("category1");
        this.category2=itemJson.getString("category2");
        this.category3=itemJson.getString("category3");
        this.category4=itemJson.getString("category4");
        
>>>>>>> ed6d9544c8bb75292349dd76fd507a0d1827cbaf
    }
}
