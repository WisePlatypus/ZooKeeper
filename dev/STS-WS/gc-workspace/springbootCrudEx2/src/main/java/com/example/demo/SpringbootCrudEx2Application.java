package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackageClasses=Classe.class)

@SpringBootApplication
public class SpringbootCrudEx2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootCrudEx2Application.class, args);
	}

}
