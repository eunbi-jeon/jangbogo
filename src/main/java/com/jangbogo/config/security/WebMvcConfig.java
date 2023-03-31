package com.jangbogo.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

//    private final long MAX_AGE_SECS = 3600L;

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("*")
//                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
//                .allowedHeaders("*")
//                .allowCredentials(true)
//                .exposedHeaders(HttpHeaders.LOCATION)
//                .maxAge(MAX_AGE_SECS);
//    }
	@Override
	public void addCorsMappings(CorsRegistry registry) {
	    registry.addMapping("/**")
	    		.allowedHeaders("Content-Type", "Authorization", "X-PINGOTHER")
	            .allowedOriginPatterns("http://localhost:*")
	            .allowedOriginPatterns("http://localhost:3000") 
	            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "TRACE", "FETCH")
	            .allowCredentials(true)
	            .exposedHeaders(HttpHeaders.LOCATION); 
	}
}

