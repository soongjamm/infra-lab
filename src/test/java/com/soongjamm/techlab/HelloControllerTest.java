package com.soongjamm.techlab;

import com.soongjamm.techlab.hello.domain.HelloRepository;
import com.soongjamm.techlab.hello.interfaces.HelloController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class HelloControllerTest {

	private MockMvc mvc;

	@BeforeEach
	void setup(@Mock HelloRepository helloRepository) {
		mvc = MockMvcBuilders.standaloneSetup(new HelloController(helloRepository))
				.alwaysDo(print())
				.build();
	}

	@Test
	void helloTest() throws Exception {
	    //given
		String url = "/hello";

		//when
		ResultActions perform = mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON));

		//then
		perform
				.andExpect(status().isOk())
				.andExpect(content().string("hello, world!"));

	}

}