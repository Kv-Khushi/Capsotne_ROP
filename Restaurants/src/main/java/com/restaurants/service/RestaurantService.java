package com.restaurants.service;

import com.restaurants.client.UserClient;
import com.restaurants.entities.Restaurant;
import com.restaurants.outdto.UserDto;
import com.restaurants.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserClient userClient;


}
