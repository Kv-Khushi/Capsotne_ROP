package com.restaurants.service;

import com.restaurants.constant.ConstantMessage;
import com.restaurants.dtoconversion.DtoConversion;
import com.restaurants.entities.Restaurant;
import com.restaurants.exception.NotFoundException;
import com.restaurants.indto.RestaurantRequest;
import com.restaurants.outdto.RestaurantResponse;
import com.restaurants.repository.RestaurantRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing restaurant operations.
 */
@Service
public class RestaurantService {

    private static final Logger logger = LogManager.getLogger(RestaurantService.class);

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private DtoConversion dtoConversion;

    /**
     * Adds a new restaurant with an optional image.
     *
     * @param restaurantRequest the request object containing restaurant details
     * @return the response object containing details of the added restaurant
     */
    public RestaurantResponse addRestaurant(final RestaurantRequest restaurantRequest,
                                            final MultipartFile image) {
        logger.info("Adding a new restaurant with details: {}", restaurantRequest);

        Restaurant restaurant = dtoConversion.convertToRestaurantEntity(restaurantRequest);
        try {
            if (image!= null && !image.isEmpty()) {
                logger.info("Processing image file for restaurant");
                restaurant.setRestaurantImage(image.getBytes());
            }
        } catch (Exception e) {
            logger.error("Error occurred while processing image file for restaurant: {}", e.getMessage());
            e.printStackTrace();
        }
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        logger.info("Restaurant added successfully with ID: {}", savedRestaurant.getRestaurantId());
        return dtoConversion.convertToRestaurantResponse(savedRestaurant);
    }


    /**
     * Retrieves all restaurants from the database.
     *
     * @return a list of response objects containing details of all restaurants
     */
    public List<RestaurantResponse> getAllRestaurants() {
        logger.info("Retrieving all restaurants");

        List<Restaurant> restaurants = restaurantRepository.findAll();
        List<RestaurantResponse> restaurantResponses = new ArrayList<>();

        for (Restaurant restaurant : restaurants) {
            RestaurantResponse restaurantResponse = dtoConversion.convertToRestaurantResponse(restaurant);
            restaurantResponses.add(restaurantResponse);
        }

        logger.info("Retrieved {} restaurants", restaurantResponses.size());
        return restaurantResponses;
    }

    /**
     * Retrieves a restaurant by its ID.
     *
     * @param restaurantId the ID of the restaurant to retrieve
     * @return the response object containing details of the restaurant
     * @throws NotFoundException if the restaurant with the given ID is not found
     */
    public RestaurantResponse getRestaurantById(final Long restaurantId) throws NotFoundException {
        logger.info("Retrieving restaurant with ID: {}", restaurantId);

        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        if (optionalRestaurant.isPresent()) {
            RestaurantResponse response = dtoConversion.convertToRestaurantResponse(optionalRestaurant.get());
            logger.info("Restaurant found with ID: {}", restaurantId);
            return response;
        } else {
            logger.error("Restaurant with ID: {} not found", restaurantId);
            throw new NotFoundException(ConstantMessage.RESTAURANT_NOT_FOUND);
        }
    }

    /**
     * Retrieves the image of a restaurant by its ID.
     * This method is designed for extension; subclasses should override with caution.
     *
     * @param restaurantId the ID of the restaurant to retrieve the image for
     * @return a byte array containing the image data
     * @throws NotFoundException if the restaurant with the given ID is not found
     */
        public byte[] getRestaurantImage(final Long restaurantId)throws NotFoundException{
            logger.info("Fetching image for restaurant with ID: {}", restaurantId);
            RestaurantResponse restaurant = getRestaurantById(restaurantId);
            return restaurant.getRestaurantImage();
        }


    /**
     * Retrieves all restaurants associated with a specific user ID.
     * This method is designed for extension; subclasses should override with caution.
     *
     * @param userId the ID of the user to retrieve the restaurants for
     * @return a list of response objects containing details of the restaurants
     */
       @Transactional
       public List<RestaurantResponse> getALlRestaurantsByUserId(final Long userId) {
        logger.info("Retrieving restaurants for user ID: {}", userId);
        List<Restaurant> restaurants = restaurantRepository.findByUserId(userId);
        List<RestaurantResponse> responseList = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            responseList.add(DtoConversion.convertToRestaurantResponse(restaurant));
        }
        logger.info("Retrieved {} restaurants for user ID: {}", responseList.size(), userId);
        return responseList;
    }



}
