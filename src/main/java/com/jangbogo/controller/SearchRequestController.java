package com.jangbogo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jangbogo.attempt.NaverShopSearch;
import com.jangbogo.dto.ItemDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class SearchRequestController {

	private final NaverShopSearch naverShopSearch;
	
	@GetMapping("/api/search")
	public List<ItemDto> getItems(@RequestParam String query){
		String resultString = naverShopSearch.search(query);
		return naverShopSearch.fromJSONtoItems(resultString);
	}
}
