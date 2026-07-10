package com.winter.word;

import org.apache.velocity.app.VelocityEngine;
import com.springinpractice.ch14.kite.CircuitBreakerTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.winter.word", "com.springinpractice"})
@EnableJpaRepositories(basePackages = {"com.winter.word", "com.springinpractice"})
@EntityScan(basePackages = {"com.winter.word", "com.springinpractice"})
public class WordApplication {

	public static void main(String[] args) {
		SpringApplication.run(WordApplication.class, args);
	}

	@Bean
	public VelocityEngine velocityEngine() {
		VelocityEngine engine = new VelocityEngine();
		engine.setProperty("resource.loader", "class");
		engine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		engine.init();
		return engine;
	}

	@Bean
	public CircuitBreakerTemplate breaker() {
		CircuitBreakerTemplate breaker = new CircuitBreakerTemplate();
		breaker.setExceptionThreshold(5);
		breaker.setTimeout(3000L);
		return breaker;
	}

}
