package com.example.FitLog.Configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("com.example.FitLog.user.model")
@EnableJpaRepositories(basePackages = "com.example.FitLog.user.persistence")
@SpringBootApplication(scanBasePackages= {
		"com.example.FitLog","com.example.FitLog"
})
public class FitLogApplication {

	static void main(String[] args) {
		SpringApplication.run(FitLogApplication.class, args);
	}
}
