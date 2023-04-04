package com.jangbogo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
<<<<<<< HEAD

=======
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling //스프링부트에서 스케줄러 작동 유도
@EnableJpaAuditing //시간 변경 자동
>>>>>>> ed6d9544c8bb75292349dd76fd507a0d1827cbaf
@SpringBootApplication
public class JangbogoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JangbogoApplication.class, args);
	}

}
