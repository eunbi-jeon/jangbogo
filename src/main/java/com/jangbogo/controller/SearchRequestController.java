package com.jangbogo.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jangbogo.dto.ItemDto;
import com.jangbogo.util.NaverShopSearch;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class SearchRequestController {

	private final NaverShopSearch naverShopSearch;
	
	@GetMapping("/api/search")
	public List<ItemDto> getItems(@RequestParam String query, 
								@RequestParam("category1") String category1,  
								@RequestParam(defaultValue = "1") int start, 						
								@RequestParam(defaultValue = "100") int display, 
									@RequestParam String sort){
		String resultString = naverShopSearch.search(query, start, display, sort);
		return naverShopSearch.fromJSONtoItems(resultString);
	}
}
