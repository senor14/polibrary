package poly.userservice;

import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.Test;

import java.util.UUID;

@SpringBootTest
class UserServiceApplicationTests {

	@Test
	void contextLoads() {

		System.out.println(UUID.randomUUID().toString().substring(0,9));
	}

}
