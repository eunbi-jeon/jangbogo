package com.jangbogo.controller;

import java.util.List;
<<<<<<< HEAD

=======
>>>>>>> ed6d9544c8bb75292349dd76fd507a0d1827cbaf
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

<<<<<<< HEAD
import com.jangbogo.attempt.NaverShopSearch;
import com.jangbogo.dto.ItemDto;
=======
import com.jangbogo.dto.ItemDto;
import com.jangbogo.util.NaverShopSearch;
>>>>>>> ed6d9544c8bb75292349dd76fd507a0d1827cbaf

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class SearchRequestController {

	private final NaverShopSearch naverShopSearch;
	
	@GetMapping("/api/search")
<<<<<<< HEAD
	public List<ItemDto> getItems(@RequestParam String query){
		String resultString = naverShopSearch.search(query);
=======
	public List<ItemDto> getItems(@RequestParam String query, @RequestParam("category1") String category1,  @RequestParam(defaultValue = "30") int display, @RequestParam(defaultValue = "asc") String sort){
		String resultString = naverShopSearch.search(query, display, sort);
>>>>>>> ed6d9544c8bb75292349dd76fd507a0d1827cbaf
		return naverShopSearch.fromJSONtoItems(resultString);
	}
}
