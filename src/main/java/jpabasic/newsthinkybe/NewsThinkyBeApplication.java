package jpabasic.newsthinkybe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NewsThinkyBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsThinkyBeApplication.class, args);
	}

}
