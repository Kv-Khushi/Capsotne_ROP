package com.restaurants;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
//import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * The entry point for the Restaurants application.
 */
@SpringBootApplication
@EnableFeignClients
public class RestaurantsApplication {
	/**
	 * The main method to run the Restaurants application.
	 *
	 * @param args the command-line arguments
	 */
	public static void main(final String[] args) {

		SpringApplication.run(RestaurantsApplication.class, args);
	}
}
