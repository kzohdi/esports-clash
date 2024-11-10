package com.spoofy.esportsclash;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(PostgreSQLTestConfiguration.class)
@SpringBootTest
class EsportsClashApplicationTests {

	@Test
	void contextLoads() {
	}

}
