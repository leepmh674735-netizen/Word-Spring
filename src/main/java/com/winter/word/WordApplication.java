package com.winter.word;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.winter.word", "com.springinpractice"})
public class WordApplication {

	public static void main(String[] args) {
		SpringApplication.run(WordApplication.class, args);
	}

}
