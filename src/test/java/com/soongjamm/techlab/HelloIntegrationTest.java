package com.soongjamm.techlab;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.DefaultUriBuilderFactory;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloIntegrationTest {

	@LocalServerPort
	private Integer port;

	@Test
	void helloIntegrationTest() {
	    //given
		TestRestTemplate template = new TestRestTemplate();
		template.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:" + port));
		String url = "/hello";

		//when
		ResponseEntity<String> entity = template.getForEntity(url, String.class);

		//then
		entity.getStatusCode().is2xxSuccessful();
		assertThat(entity.getBody()).isEqualTo("hello, world!");
	}
}
