package com.jangbogo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	@GetMapping("/api/hello")
	public String test() {
		System.out.println("컨트롤러 실행");
		return "Hello, world!";
		
		}
}
