package kr.co.seoulit.insa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class InsaApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsaApplication.class, args);
	}	
}