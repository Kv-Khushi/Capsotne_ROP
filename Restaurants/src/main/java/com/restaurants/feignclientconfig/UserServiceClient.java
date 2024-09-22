package com.restaurants.feignclientconfig;

import com.restaurants.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * Feign client for communicating with the User Service.
 */
@FeignClient(name = "user-service", url = "http://localhost:8080")
public interface UserServiceClient {
    /**
     * Retrieves a user by their ID from the User Service.
     *
     * @param userId the ID of the user to retrieve
     * @return the UserResponse object containing user details
     */
    @GetMapping("/users/getUser/{userId}")
    UserResponse getUserById(@PathVariable("userId") Long userId);
}
