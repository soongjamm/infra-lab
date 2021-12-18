package com.soongjamm.techlab.hello.interfaces;

import com.soongjamm.techlab.hello.domain.Hello;
import com.soongjamm.techlab.hello.domain.HelloRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	private HelloRepository helloRepository;

	public HelloController(HelloRepository helloRepository) {
		this.helloRepository = helloRepository;
	}

	@GetMapping("/hello")
	public String hello() {
		Hello hello = new Hello();
		helloRepository.save(hello);
		return hello.getHello();
	}
}
