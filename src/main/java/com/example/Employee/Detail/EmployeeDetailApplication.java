package com.example.Employee.Detail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories


public class EmployeeDetailApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeDetailApplication.class, args);

	}

}
