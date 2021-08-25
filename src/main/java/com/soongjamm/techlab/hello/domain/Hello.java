package com.soongjamm.techlab.hello.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Hello {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String hello;

	public Hello() {
		this.hello = "hello, world!";
	}

	public Long getId() {
		return id;
	}

	public String getHello() {
		return hello;
	}
}
