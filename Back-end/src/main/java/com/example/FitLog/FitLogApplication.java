package com.example.FitLog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("user.model")
@EnableJpaRepositories(basePackages = "user.persistence")
@SpringBootApplication(scanBasePackages= {
		"user","com.example.FitLog"
})
public class FitLogApplication {

	static void main(String[] args) {
		SpringApplication.run(FitLogApplication.class, args);
	}
}
