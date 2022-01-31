package com.choitaek.meethere.meethere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration //시간 작동 변경이 가능하도록함
public class MeethereApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeethereApplication.class, args);
	}

}
