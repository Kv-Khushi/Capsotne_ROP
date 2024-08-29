package com.restaurants.service;


import com.restaurants.constant.ConstantMessage;
import com.restaurants.dtoconversion.DtoConversion;
import com.restaurants.entities.FoodCategory;
import com.restaurants.entities.RestaurantMenu;
import com.restaurants.exception.NotFoundException;
import com.restaurants.indto.FoodCategoryRequest;
import com.restaurants.indto.RestaurantMenuRequest;
import com.restaurants.outdto.FoodCategoryResponse;
import com.restaurants.outdto.RestaurantMenuResponse;
import com.restaurants.outdto.RestaurantResponse;
import com.restaurants.repository.RestaurantMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class RestaurantMenuService {


    @Autowired
    private RestaurantMenuRepository restaurantMenuRepository;

    @Autowired
    private DtoConversion dtoConversion;

    public RestaurantMenuResponse addFoodItem(RestaurantMenuRequest restaurantMenuRequest) {
        RestaurantMenu restaurantMenu = dtoConversion.convertToRestaurantMenuEntity(restaurantMenuRequest);
        RestaurantMenu savedRestaurantMenu = restaurantMenuRepository.save(restaurantMenu);
        return dtoConversion.convertToRestaurantMenuResponse(savedRestaurantMenu);
    }

    public void deleteFoodItem(Long itemId) throws NotFoundException {
        // Ensure the item exists before trying to delete
        if (!restaurantMenuRepository.existsById(itemId)) {
            throw new NotFoundException(ConstantMessage.FOOD_ITEM_NOT_FOUND);
        }
        restaurantMenuRepository.deleteById(itemId);
    }



}
