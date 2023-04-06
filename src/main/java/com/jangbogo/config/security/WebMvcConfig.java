package com.jangbogo.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

    private final long MAX_AGE_SECS = 3600;

    //Cors 설정
    @Override
	public void addCorsMappings(CorsRegistry registry) {
	    registry.addMapping("/**")
	            .allowedOriginPatterns("http://localhost:3000")
	            .allowedHeaders("Content-Type", "Authorization", "X-PINGOTHER")
	            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "TRACE", "FETCH")
	            .allowCredentials(true)
	            .exposedHeaders(HttpHeaders.LOCATION)
                .maxAge(MAX_AGE_SECS);
	}

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:/Users/jeon-eunbi/Desktop/profile");
    }
}
