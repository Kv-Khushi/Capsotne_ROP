package com.restaurants.service;
import com.restaurants.dto.UserResponse;
import com.restaurants.enums.UserRole;
import com.restaurants.exception.AlreadyExistsException;
import com.restaurants.exception.InvalidRequestException;
import com.restaurants.exception.ResourceNotFoundException;
import com.restaurants.constant.ConstantMessage;
import com.restaurants.dtoconversion.DtoConversion;
import com.restaurants.entities.Restaurant;
import com.restaurants.dto.RestaurantRequest;
import com.restaurants.dto.RestaurantResponse;
import com.restaurants.exception.UnauthorizedException;
import com.restaurants.feignclientconfig.UserServiceClient;
import com.restaurants.repository.RestaurantRepository;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class RestaurantService {
    

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
        log.info("Adding a new restaurant with details: {}", restaurantRequest);

        try {
            UserResponse userResponse = userServiceClient.getUserById(restaurantRequest.getUserId());
            String userRole = userResponse.getUserRole();

            if (UserRole.CUSTOMER.toString().equals(userRole)) {
                log.error("User with ID {} is a CUSTOMER and cannot register a restaurant", restaurantRequest.getUserId());
                throw new UnauthorizedException(ConstantMessage.UNAUTHORIZED_USER);
            }
        }
        catch(FeignException.NotFound ex){
            throw new ResourceNotFoundException(ConstantMessage.USER_NOT_FOUND);
        }
        catch(FeignException ex){
            throw new RuntimeException(ConstantMessage.USER_SERVICE_DOWN);
        }

        boolean exists = restaurantRepository.existsByRestaurantNameIgnoreCase(restaurantRequest.getRestaurantName());

        if (exists) {
            log.error("Duplicate restaurant: {} already exists", restaurantRequest.getRestaurantName());
            throw new AlreadyExistsException(ConstantMessage.RESTAURANT_ALREADY_EXISTS);
        }

        Restaurant restaurant = dtoConversion.convertToRestaurantEntity(restaurantRequest);
        log.info("Converted restaurant entity: {}", restaurant);

        if(image.isEmpty() || image.getContentType() == null) {
            throw new ResourceNotFoundException(ConstantMessage.INVALID_IMAGE);
        }
        try {
                String contentType = image.getContentType();
                if (!(contentType.equals("image/jpeg") || contentType.equals("image/png"))) {
                    System.out.println("should not run ");
                    log.error("Invalid image type: {}. Only JPG and PNG are allowed.", contentType);
                    throw new InvalidRequestException(ConstantMessage.INVALID_IMAGE_FORMAT);
                }
                restaurant.setRestaurantImage(image.getBytes());
        }
        catch (Exception e) {
            System.out.println(e.getClass());
            log.error("Error occurred while processing image file for restaurant: {}", e.getMessage());
            throw new RuntimeException("Image processing failed");
        }
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        log.info("Restaurant added successfully with ID: {}", savedRestaurant.getRestaurantId());
        return dtoConversion.convertToRestaurantResponse(savedRestaurant);
    }


    /**
     * Retrieves all restaurants from the database.
     *
     * @return a list of response objects containing details of all restaurants
     */
    public List<RestaurantResponse> getAllRestaurants() {
        log.info("Retrieving all restaurants");

        List<Restaurant> restaurants = restaurantRepository.findAll();
        List<RestaurantResponse> restaurantResponses = new ArrayList<>();

        for (Restaurant restaurant : restaurants) {
            RestaurantResponse restaurantResponse = dtoConversion.convertToRestaurantResponse(restaurant);
            restaurantResponses.add(restaurantResponse);
        }

        log.info("Retrieved {} restaurants", restaurantResponses.size());
        return restaurantResponses;
    }

    /**
     * Retrieves a restaurant by its ID.
     *
     * @param restaurantId the ID of the restaurant to retrieve
     * @return the response object containing details of the restaurant
     * @throws ResourceNotFoundException if the restaurant with the given ID is not found
     */
    public RestaurantResponse getRestaurantById(final Long restaurantId){
        log.info("Retrieving restaurant with ID: {}", restaurantId);

        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        if (optionalRestaurant.isPresent()) {
            RestaurantResponse response = dtoConversion.convertToRestaurantResponse(optionalRestaurant.get());
            log.info("Restaurant found with ID: {}", restaurantId);
            return response;
        } else {
            log.error("Restaurant with ID: {} not found", restaurantId);
            throw new ResourceNotFoundException(ConstantMessage.RESTAURANT_NOT_FOUND);
        }
    }



    /**
     * Retrieves the image of a restaurant by its ID.
     * This method is designed for extension; subclasses should override with caution.
     *
     * @param restaurantId the ID of the restaurant to retrieve the image for
     * @return a byte array containing the image data
     * @throws ResourceNotFoundException if the restaurant with the given ID is not found
     */

        public byte[] getRestaurantImage(final Long restaurantId){
            log.info("Fetching image for restaurant with ID: {}", restaurantId);
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
        log.info("Retrieving restaurants for user ID: {}", userId);
        List<Restaurant> restaurants = restaurantRepository.findByUserId(userId);
        List<RestaurantResponse> responseList = new ArrayList<>();
           log.info("Restaurants retrieved: {}", restaurants);
        for (Restaurant restaurant : restaurants) {
            DtoConversion dtoConversion= new DtoConversion();
            responseList.add(dtoConversion.convertToRestaurantResponse(restaurant));
        }
        log.info("Retrieved {} restaurants for user ID: {}", responseList.size(), userId);
        return responseList;
    }
}

