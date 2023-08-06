package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SrsAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SrsAppApplication.class, args);
		System.out.println("Spring-Boot Unit-Testing using MockMvc running on port No: 8585...");
	}

}
