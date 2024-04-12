package com.fiap58.producao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
public class ProducaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProducaoApplication.class, args);
	}

}
