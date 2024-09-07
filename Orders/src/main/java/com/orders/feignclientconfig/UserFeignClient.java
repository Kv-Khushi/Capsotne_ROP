package com.orders.feignclientconfig;


import com.orders.outdto.AddressResponse;
import com.orders.outdto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "user-service", url = "http://localhost:8080")
public interface UserFeignClient {

    @GetMapping("/users/getUser/{userId}")
    UserResponse getUserById(@PathVariable("userId") Long userId);

    @GetMapping("address/getAddress/{userId}")
    List<AddressResponse> getAllAddressesForUser(@PathVariable("userId") Long userId);
}
