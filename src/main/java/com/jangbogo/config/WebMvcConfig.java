package com.jangbogo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        addMapping() : 지정한 패턴에 맞는 페이지에 대해서 접근 권한 확인
//        allowedOrigins(url) : 접근 허용할 외부 url
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders("*");
//                .allowedOriginPatterns("*")
//                .allowCredentials(true).maxAge(3000);
    }
}
