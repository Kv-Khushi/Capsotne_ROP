
package com.restaurants.service;

import com.restaurants.constant.ConstantMessage;
import com.restaurants.dto.RestaurantRequest;
import com.restaurants.dto.RestaurantResponse;
import com.restaurants.dto.UserResponse;
import com.restaurants.entities.Restaurant;
import com.restaurants.exception.InvalidRequestException;
import com.restaurants.exception.ResourceNotFoundException;
import com.restaurants.exception.UnauthorizedException;
import com.restaurants.feignclientconfig.UserServiceClient;
import com.restaurants.repository.RestaurantRepository;
import com.restaurants.dtoconversion.DtoConversion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class RestaurantServiceTest {

    @InjectMocks
    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private DtoConversion dtoConversion;

    @Mock
    private UserServiceClient userServiceClient;

    @Mock
    private MultipartFile image;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addRestaurantSuccessTest() throws Exception {
        // Set up request and expected values
        RestaurantRequest request = new RestaurantRequest();
        request.setUserId(1L);
        request.setRestaurantName("Test Restaurant");
        request.setRestaurantAddress("Test Address");
        request.setContactNumber("9876543210");
        request.setRestaurantDescription("Test Description");
        request.setOpeningHour("10:00 AM");

        UserResponse userResponse = new UserResponse();
        userResponse.setUserRole("RESTAURANT_OWNER");

        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(1L);

        RestaurantResponse response = new RestaurantResponse();
        response.setRestaurantId(1L);

        // Mocking behavior
        when(userServiceClient.getUserById(anyLong())).thenReturn(userResponse);
        when(dtoConversion.convertToRestaurantEntity(any(RestaurantRequest.class))).thenReturn(restaurant);
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);
//        when(dtoConversion.convertToRestaurantResponse(any(Restaurant.class))).thenReturn(response);

        // Calling the service method
        RestaurantResponse result = restaurantService.addRestaurant(request, null);

        // Asserts and verifications
        assertNotNull(result);
        assertEquals(1L, result.getRestaurantId());
        verify(restaurantRepository, times(1)).save(any(Restaurant.class));
    }

    @Test
    void addRestaurantInvalidImageFormatTest() throws Exception {
        RestaurantRequest request = new RestaurantRequest();
        request.setUserId(1L);
        request.setRestaurantName("Test Restaurant");
        request.setRestaurantAddress("Test Address");
        request.setContactNumber("9876543210");
        request.setRestaurantDescription("Test Description");
        request.setOpeningHour("10:00 AM");

        UserResponse userResponse = new UserResponse();
        userResponse.setUserRole("ADMIN");

        when(userServiceClient.getUserById(anyLong())).thenReturn(userResponse);
        when(dtoConversion.convertToRestaurantEntity(any(RestaurantRequest.class))).thenReturn(new Restaurant());

        when(image.getContentType()).thenReturn("text/plain");

        assertThrows(InvalidRequestException.class, () -> {
            restaurantService.addRestaurant(request, image);
        });
    }

    @Test
    void addRestaurantUnauthorizedUserTest() throws Exception {
        RestaurantRequest request = new RestaurantRequest();
        request.setUserId(1L);
        request.setRestaurantName("Test Restaurant");
        request.setRestaurantAddress("Test Address");
        request.setContactNumber("9876543210");
        request.setRestaurantDescription("Test Description");
        request.setOpeningHour("10:00 AM");

        UserResponse userResponse = new UserResponse();
        userResponse.setUserRole("CUSTOMER");

        when(userServiceClient.getUserById(anyLong())).thenReturn(userResponse);

        assertThrows(UnauthorizedException.class, () -> {
            restaurantService.addRestaurant(request, image);
        });
    }

    @Test
    void getAllRestaurantsTest() {
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setRestaurantId(1L);

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setRestaurantId(2L);

        List<Restaurant> restaurantList = new ArrayList<>();
        restaurantList.add(restaurant1);
        restaurantList.add(restaurant2);

        RestaurantResponse response1 = new RestaurantResponse();
        response1.setRestaurantId(1L);

        RestaurantResponse response2 = new RestaurantResponse();
        response2.setRestaurantId(2L);

        when(restaurantRepository.findAll()).thenReturn(restaurantList);
//        when(dtoConversion.convertToRestaurantResponse(any(Restaurant.class)))
//                .thenReturn(response1)
//                .thenReturn(response2);

        List<RestaurantResponse> result = restaurantService.getAllRestaurants();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getRestaurantId());
        assertEquals(2L, result.get(1).getRestaurantId());
    }

    @Test
    void getRestaurantByIdSuccessTest() throws ResourceNotFoundException {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(1L);

        RestaurantResponse response = new RestaurantResponse();
        response.setRestaurantId(1L);

        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurant));
//        when(dtoConversion.convertToRestaurantResponse(any(Restaurant.class))).thenReturn(response);

        RestaurantResponse result = restaurantService.getRestaurantById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getRestaurantId());
    }

    @Test
    void getRestaurantByIdNotFoundTest() {
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            restaurantService.getRestaurantById(1L);
        });
    }





    @Test
    void getALlRestaurantsByUserIdTest() {
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setRestaurantId(1L);

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setRestaurantId(2L);

        List<Restaurant> restaurantList = new ArrayList<>();
        restaurantList.add(restaurant1);
        restaurantList.add(restaurant2);

        RestaurantResponse response1 = new RestaurantResponse();
        response1.setRestaurantId(1L);

        RestaurantResponse response2 = new RestaurantResponse();
        response2.setRestaurantId(2L);

        when(restaurantRepository.findAllByUserId(anyLong())).thenReturn(restaurantList);

        List<RestaurantResponse> result = restaurantService.getALlRestaurantsByUserId(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getRestaurantId());
        assertEquals(2L, result.get(1).getRestaurantId());
    }

    @Test
    void addRestaurantWithNullUserRoleTest() throws Exception {
        RestaurantRequest request = new RestaurantRequest();
        request.setUserId(1L);
        request.setRestaurantName("Test Restaurant");
        request.setRestaurantAddress("Test Address");
        request.setContactNumber("9876543210");
        request.setRestaurantDescription("Test Description");
        request.setOpeningHour("10:00 AM");

        UserResponse userResponse = new UserResponse();
        userResponse.setUserRole(null); // Simulate a null role

        when(userServiceClient.getUserById(anyLong())).thenReturn(userResponse);


    }
    @Test
    void addRestaurantWhenUserServiceFailsTest() throws Exception {
        RestaurantRequest request = new RestaurantRequest();
        request.setUserId(1L);
        request.setRestaurantName("Test Restaurant");
        request.setRestaurantAddress("Test Address");
        request.setContactNumber("9876543210");
        request.setRestaurantDescription("Test Description");
        request.setOpeningHour("10:00 AM");

        when(userServiceClient.getUserById(anyLong())).thenThrow(new RuntimeException("User service unavailable"));

        assertThrows(RuntimeException.class, () -> {
            restaurantService.addRestaurant(request, image);
        });
    }

    @Test
    void getAllRestaurantsWithEmptyListTest() {
        when(restaurantRepository.findAll()).thenReturn(new ArrayList<>());
        List<RestaurantResponse> result = restaurantService.getAllRestaurants();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    @Test
    void getALlRestaurantsByUserIdWhenNoRestaurantsFoundTest() {
        when(restaurantRepository.findAllByUserId(anyLong())).thenReturn(new ArrayList<>());
        List<RestaurantResponse> result = restaurantService.getALlRestaurantsByUserId(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    }
