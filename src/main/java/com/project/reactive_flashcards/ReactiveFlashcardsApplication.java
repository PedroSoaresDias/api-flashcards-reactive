package com.project.reactive_flashcards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
@EnableReactiveMongoAuditing(dateTimeProviderRef = "dateTimeProvider")
public class ReactiveFlashcardsApplication {

	public static void main(String[] args) {
		Dotenv dotenv;

		if (System.getenv("DOCKER_ENV") != null) {
			dotenv = Dotenv.configure()
					.directory("/app")
					.load();
		} else {
			dotenv = Dotenv.configure()
					.directory("./")
					.load();
		}
		System.setProperty("MONGODB_URI", dotenv.get("MONGODB_URI"));
		SpringApplication.run(ReactiveFlashcardsApplication.class, args);
	}

}
