package com.restaurants.service;
import com.restaurants.dto.UserResponse;
import com.restaurants.enums.UserRole;
import com.restaurants.exception.InvalidRequestException;
import com.restaurants.exception.NotFoundException;
import com.restaurants.constant.ConstantMessage;
import com.restaurants.dtoconversion.DtoConversion;
import com.restaurants.entities.Restaurant;
import com.restaurants.dto.RestaurantRequest;
import com.restaurants.dto.RestaurantResponse;
import com.restaurants.exception.UnauthorizedException;
import com.restaurants.feignclientconfig.UserServiceClient;
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

    @Autowired
    private UserServiceClient userServiceClient;

    /**
     * Adds a new restaurant with an optional image.
     *
     * @param restaurantRequest the request object containing restaurant details
     * @return the response object containing details of the added restaurant
     */

    public RestaurantResponse addRestaurant(final RestaurantRequest restaurantRequest, final MultipartFile image) {
        logger.info("Adding a new restaurant with details: {}", restaurantRequest);

        // Fetch the user details
        UserResponse userResponse = userServiceClient.getUserById(restaurantRequest.getUserId());
        String userRole = userResponse.getUserRole();

        // Check if the user is a customer
        if (UserRole.CUSTOMER.name().equals(userRole)) {
            logger.error("User with ID {} is a CUSTOMER and cannot register a restaurant", restaurantRequest.getUserId());
            throw new UnauthorizedException(ConstantMessage.UNAUTHORIZED_USER);
        }

        // Convert the restaurant request to an entity
        Restaurant restaurant = dtoConversion.convertToRestaurantEntity(restaurantRequest);


        try {
            if (image != null && !image.isEmpty()) {
                String contentType = image.getContentType();
                if (contentType == null || !(contentType.equals("image/jpeg") || contentType.equals("image/png"))) {
                    logger.error("Invalid image type: {}. Only JPG and PNG are allowed.", contentType);
                    throw new InvalidRequestException(ConstantMessage.INVALID_IMAGE_FORMAT);
                }
                // Process the image if validation passes
                restaurant.setRestaurantImage(image.getBytes());
            }
        } catch (InvalidRequestException e) {
            // Ensure not to catch the specific exception unless for logging
            throw e;  // Re-throw if caught
        } catch (Exception e) {
            logger.error("Error occurred while processing image file for restaurant: {}", e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Image processing failed");
        }


        // Save the restaurant
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        logger.info("Restaurant added successfully with ID: {}", savedRestaurant.getRestaurantId());

        // Convert the saved entity to a response DTO
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
        List<Restaurant> restaurants = restaurantRepository.findAllByUserId(userId);
        List<RestaurantResponse> responseList = new ArrayList<>();
           logger.info("Restaurants retrieved: {}", restaurants);
        for (Restaurant restaurant : restaurants) {
            responseList.add(DtoConversion.convertToRestaurantResponse(restaurant));
        }
        logger.info("Retrieved {} restaurants for user ID: {}", responseList.size(), userId);
        return responseList;
    }



}
