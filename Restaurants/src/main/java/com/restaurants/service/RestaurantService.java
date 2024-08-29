package com.restaurants.service;

import com.restaurants.constant.ConstantMessage;
import com.restaurants.dtoconversion.DtoConversion;
import com.restaurants.entities.Restaurant;
import com.restaurants.exception.NotFoundException;
import com.restaurants.indto.RestaurantRequest;
import com.restaurants.outdto.RestaurantResponse;
import com.restaurants.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private DtoConversion dtoConversion;

    public RestaurantResponse addRestaurant(RestaurantRequest restaurantRequest) {

        Restaurant restaurant = dtoConversion.convertToRestaurantEntity(restaurantRequest);
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return dtoConversion.convertToRestaurantResponse(savedRestaurant);
    }

    public List<RestaurantResponse> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();

        List<RestaurantResponse> restaurantResponses = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            RestaurantResponse restaurantResponse = dtoConversion.convertToRestaurantResponse(restaurant);
            restaurantResponses.add(restaurantResponse);
        }

        return restaurantResponses;
    }

    public RestaurantResponse getRestaurantById(Long restaurantId) throws NotFoundException {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        if (optionalRestaurant.isPresent()) {
            return dtoConversion.convertToRestaurantResponse(optionalRestaurant.get());
        } else {
            throw new NotFoundException(ConstantMessage.RESTAURANT_NOT_FOUND);
        }
    }

}
