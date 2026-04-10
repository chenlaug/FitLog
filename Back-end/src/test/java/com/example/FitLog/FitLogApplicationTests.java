package com.example.FitLog;

import com.example.FitLog.Configuration.FitLogApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = FitLogApplication.class)
@ActiveProfiles("test")
class FitLogApplicationTests {

	@Test
	void contextLoads() {
	}

}
