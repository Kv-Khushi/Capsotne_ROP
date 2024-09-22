
package com.restaurants.service;


import com.restaurants.dto.RestaurantRequest;
import com.restaurants.dto.RestaurantResponse;
import com.restaurants.dto.UserResponse;
import com.restaurants.entities.Restaurant;
import com.restaurants.enums.UserRole;
import com.restaurants.exception.AlreadyExistsException;
import com.restaurants.exception.InvalidRequestException;
import com.restaurants.exception.ResourceNotFoundException;
import com.restaurants.exception.UnauthorizedException;
import com.restaurants.feignclientconfig.UserServiceClient;
import com.restaurants.repository.RestaurantRepository;
import com.restaurants.dtoconversion.DtoConversion;
import feign.FeignException;
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

    //private RestaurantRequest restaurantRequest;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void testAddRestaurant_Success() throws Exception {
        // Arrange
        RestaurantRequest restaurantRequest = new RestaurantRequest();
        restaurantRequest.setUserId(1L);
        restaurantRequest.setRestaurantName("Test Restaurant");
        restaurantRequest.setRestaurantAddress("123 Main Street");
        restaurantRequest.setContactNumber("9876543210");
        restaurantRequest.setRestaurantDescription("A test restaurant");

        UserResponse userResponse = new UserResponse();
        userResponse.setUserRole(UserRole.RESTAURANT_OWNER.toString());

        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantName("Test Restaurant");

        when(userServiceClient.getUserById(anyLong())).thenReturn(userResponse);
        when(restaurantRepository.existsByRestaurantNameIgnoreCase(anyString())).thenReturn(false);
        when(dtoConversion.convertToRestaurantEntity(any(RestaurantRequest.class))).thenReturn(restaurant);
        when(image.isEmpty()).thenReturn(false);
        when(image.getContentType()).thenReturn("image/jpeg");
        when(image.getBytes()).thenReturn(new byte[10]);

        Restaurant savedRestaurant = new Restaurant();
        savedRestaurant.setRestaurantId(1L);

        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(savedRestaurant);
        when(dtoConversion.convertToRestaurantResponse(any(Restaurant.class))).thenReturn(new RestaurantResponse());

        // Act
        RestaurantResponse response = restaurantService.addRestaurant(restaurantRequest, image);

        // Assert
        assertNotNull(response);
        verify(restaurantRepository).save(any(Restaurant.class));
    }

    @Test
    void testAddRestaurant_UnauthorizedUser() {
        // Arrange
        RestaurantRequest restaurantRequest = new RestaurantRequest();
        restaurantRequest.setUserId(1L);

        UserResponse userResponse = new UserResponse();
        userResponse.setUserRole(UserRole.CUSTOMER.toString());

        when(userServiceClient.getUserById(anyLong())).thenReturn(userResponse);

        // Act & Assert
        assertThrows(UnauthorizedException.class, () -> {
            restaurantService.addRestaurant(restaurantRequest, image);
        });
        verify(restaurantRepository, never()).save(any(Restaurant.class));
    }

    @Test
    void testAddRestaurant_UserNotFound() {
        // Arrange
        RestaurantRequest restaurantRequest = new RestaurantRequest();
        restaurantRequest.setUserId(1L);

        when(userServiceClient.getUserById(anyLong())).thenThrow(FeignException.NotFound.class);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            restaurantService.addRestaurant(restaurantRequest, image);
        });
        verify(restaurantRepository, never()).save(any(Restaurant.class));
    }

    @Test
    void testAddRestaurant_ImageProcessingFailure() throws Exception {
        // Arrange
        RestaurantRequest restaurantRequest = new RestaurantRequest();
        restaurantRequest.setUserId(1L);
        restaurantRequest.setRestaurantName("Valid Name");

        UserResponse userResponse = new UserResponse();
        userResponse.setUserRole(UserRole.RESTAURANT_OWNER.toString());

        when(userServiceClient.getUserById(anyLong())).thenReturn(userResponse);
        when(restaurantRepository.existsByRestaurantNameIgnoreCase(anyString())).thenReturn(false);
        when(image.isEmpty()).thenReturn(false);
        when(image.getContentType()).thenReturn("image/jpeg");
        when(image.getBytes()).thenThrow(new RuntimeException("Error during image processing")); // Simulate exception

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            restaurantService.addRestaurant(restaurantRequest, image);
        });
        verify(restaurantRepository, never()).save(any(Restaurant.class));
    }

    @Test
    void testAddRestaurant_EmptyImage() {
        // Arrange
        RestaurantRequest restaurantRequest = new RestaurantRequest();
        restaurantRequest.setUserId(1L);
        restaurantRequest.setRestaurantName("Valid Name");

        UserResponse userResponse = new UserResponse();
        userResponse.setUserRole(UserRole.RESTAURANT_OWNER.toString());

        when(userServiceClient.getUserById(anyLong())).thenReturn(userResponse);
        when(restaurantRepository.existsByRestaurantNameIgnoreCase(anyString())).thenReturn(false);
        when(image.isEmpty()).thenReturn(true); // Image is empty

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            restaurantService.addRestaurant(restaurantRequest, image);
        });
        verify(restaurantRepository, never()).save(any(Restaurant.class));
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

        List<RestaurantResponse> result = restaurantService.getAllRestaurants();

        assertNotNull(result);
      assertEquals(2, result.size());
    }

    @Test
    void testGetRestaurantById_Found() {
        // Arrange
        Long restaurantId = 1L;
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(restaurantId);
        restaurant.setRestaurantName("Test Restaurant");

        RestaurantResponse restaurantResponse = new RestaurantResponse();
        restaurantResponse.setRestaurantId(restaurantId);

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(dtoConversion.convertToRestaurantResponse(restaurant)).thenReturn(restaurantResponse);

        // Act
        RestaurantResponse result = restaurantService.getRestaurantById(restaurantId);

        // Assert
        assertNotNull(result);
        assertEquals(restaurantId, result.getRestaurantId());
        verify(restaurantRepository).findById(restaurantId);
        verify(dtoConversion).convertToRestaurantResponse(restaurant);
    }

    @Test
    void testGetRestaurantById_NotFound() {
        // Arrange
        Long restaurantId = 1L;

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            restaurantService.getRestaurantById(restaurantId);
        });

        verify(restaurantRepository).findById(restaurantId);
        verify(dtoConversion, never()).convertToRestaurantResponse(any(Restaurant.class));
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

        when(restaurantRepository.findByUserId(anyLong())).thenReturn(restaurantList);

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
        when(restaurantRepository.findByUserId(anyLong())).thenReturn(new ArrayList<>());
        List<RestaurantResponse> result = restaurantService.getALlRestaurantsByUserId(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    }
