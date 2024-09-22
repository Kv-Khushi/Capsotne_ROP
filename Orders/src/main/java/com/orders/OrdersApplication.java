package com.orders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * The entry point of the Orders application.
 * This class contains the main method which is used to start the Spring Boot application.
 */
@EnableFeignClients
@SpringBootApplication
public class OrdersApplication {


	/**
	 * The main method that starts the Spring Boot application.
	 *
	 * @param args command-line arguments passed to the application
	 */
	public static void main(final String[] args) {
		SpringApplication.run(OrdersApplication.class, args);
	}

}
