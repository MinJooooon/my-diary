package com.mydiary;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"00. MydiaryApplication"})
@EnableJpaAuditing
@RestController
@SpringBootApplication
public class MydiaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MydiaryApplication.class, args);
	}

	@ApiOperation(value = "MydiaryApplication")
	@GetMapping("/")
	@ResponseStatus(HttpStatus.OK)
	public String status() {
		return "MydiaryApplication";
	}
}
