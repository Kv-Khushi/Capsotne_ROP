package com.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point of the Spring Boot application.
 * <p>
 * This class contains the {@link #main(String[])} method that is used to launch the Spring Boot application.
 * The {@link SpringBootApplication} annotation is used to mark this class as a Spring Boot application
 * and to enable auto-configuration, component scanning, and configuration properties.
 * </p>
 */
@SpringBootApplication
public class UsersApplication {

	/**
	 * The main method that serves as the entry point of the application.
	 * <p>
	 * This method initializes the Spring application context and starts the embedded server.
	 * </p>
	 *
	 * @param args command-line arguments passed to the application
	 */
	public static void main(final String[] args) {
			SpringApplication.run(UsersApplication.class, args);
	}
}
