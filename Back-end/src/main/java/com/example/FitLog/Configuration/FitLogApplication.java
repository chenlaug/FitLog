package com.example.FitLog.Configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"com.example.FitLog.user.model",
		"com.example.FitLog.workout.model",
		"com.example.FitLog.exercise.model"}
)
@EnableJpaRepositories(basePackages = {
		"com.example.FitLog.user.persistence",
		"com.example.FitLog.exercise.persistence",
		"com.example.FitLog.workout.persistence"
})

@SpringBootApplication(scanBasePackages= {
		"com.example.FitLog","com.example.FitLog"
})
public class FitLogApplication {

	static void main(String[] args) {
		SpringApplication.run(FitLogApplication.class, args);
	}
}
