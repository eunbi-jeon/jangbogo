package com.jangbogo.attempt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling //스프링부트에서 스케줄러 작동 유도
@EnableJpaAuditing //시간 변경 자동
@SpringBootApplication
public class TimeUpdateApplication {
	public static void main(String[] args) {
		SpringApplication.run(TimeUpdateApplication.class, args);
	}
}
