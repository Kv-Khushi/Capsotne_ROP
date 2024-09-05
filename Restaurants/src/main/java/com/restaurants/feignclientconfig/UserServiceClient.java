package com.restaurants.feignclientconfig;

import com.restaurants.dto.outdto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "http://localhost:8080")
public interface UserServiceClient {
    @GetMapping("/users/getUser/{userId}")
    UserResponse getUserById(@PathVariable("userId") Long userId);
}
