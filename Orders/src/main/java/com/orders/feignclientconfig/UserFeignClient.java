package com.orders.feignclientconfig;


import com.orders.dto.AddressResponse;
import com.orders.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Feign client for interacting with the user-service API.
 */
@FeignClient(name = "user-service", url = "http://localhost:8080")
public interface UserFeignClient {


    /**
     * Fetches the user details by the given user ID.
     *
     * @param userId the ID of the user
     * @return the UserResponse containing user details
     */
    @GetMapping("/users/getUser/{userId}")
    UserResponse getUserById(@PathVariable("userId") Long userId);


    /**
     * Fetches all the addresses for the given user ID.
     *
     * @param userId the ID of the user
     * @return a list of AddressResponse containing addresses for the user
     */
    @GetMapping("address/getAddress/{userId}")
    List<AddressResponse> getAllAddressesForUser(@PathVariable("userId") Long userId);

    @PutMapping("users/{userId}/wallet")
    void updateWalletBalance(@PathVariable Long userId, @RequestBody Double newBalance);

}
