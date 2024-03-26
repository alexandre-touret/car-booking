package info.touret.ai.carbooking;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CarBookingApplicationTests {

	@Test
	void contextLoads() {
		System.err.println(System.getenv("SPRING_AI_AZURE_OPENAI_API_KEY"));
	}

}
