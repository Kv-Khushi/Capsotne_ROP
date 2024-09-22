package com.restaurants.service;

import com.restaurants.dtoconversion.DtoConversion;
import com.restaurants.entities.RestaurantMenu;
import com.restaurants.exception.AlreadyExistsException;
import com.restaurants.exception.ResourceNotFoundException;
import com.restaurants.dto.RestaurantMenuRequest;
import com.restaurants.dto.RestaurantMenuResponse;
import com.restaurants.repository.FoodCategoryRepository;
import com.restaurants.repository.RestaurantMenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RestaurantMenuServiceTest {

    @Mock
    private RestaurantMenuRepository restaurantMenuRepository;

    @Mock
    private DtoConversion dtoConversion;

    @Mock
    private FoodCategoryRepository foodCategoryRepository;

    @InjectMocks
    private RestaurantMenuService restaurantMenuService;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddFoodItem_Success() throws Exception {
        // Arrange
        RestaurantMenuRequest menuRequest = new RestaurantMenuRequest();
        menuRequest.setItemName("Test Item");
        menuRequest.setPrice(10.0);
        menuRequest.setDescription("A test food item");
        menuRequest.setCategoryId(1L);
        menuRequest.setRestaurantId(1L);
        menuRequest.setVegNonVeg(true);

        MultipartFile foodImage = mock(MultipartFile.class);
        when(foodImage.isEmpty()).thenReturn(false);
        when(foodImage.getBytes()).thenReturn(new byte[10]);

        when(restaurantMenuRepository.existsByRestaurantIdAndItemNameIgnoreCase(1L, "Test Item")).thenReturn(false);
        when(foodCategoryRepository.existsById(1L)).thenReturn(true); // Mock category existence
        RestaurantMenu restaurantMenu = new RestaurantMenu();
        when(dtoConversion.convertToRestaurantMenuEntity(menuRequest)).thenReturn(restaurantMenu);
        when(restaurantMenuRepository.save(restaurantMenu)).thenReturn(restaurantMenu);
        when(dtoConversion.convertToRestaurantMenuResponse(restaurantMenu)).thenReturn(new RestaurantMenuResponse());

        // Act
        RestaurantMenuResponse response = restaurantMenuService.addFoodItem(menuRequest, foodImage);

        // Assert
        assertNotNull(response);
        verify(restaurantMenuRepository).save(restaurantMenu);
    }

    @Test
    void testAddFoodItem_CategoryNotFound() {
        // Arrange
        RestaurantMenuRequest menuRequest = new RestaurantMenuRequest();
        menuRequest.setItemName("New Item");
        menuRequest.setCategoryId(999L); // Non-existing category
        menuRequest.setRestaurantId(1L);

        when(restaurantMenuRepository.existsByRestaurantIdAndItemNameIgnoreCase(anyLong(), anyString())).thenReturn(false);
        when(foodCategoryRepository.existsById(999L)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            restaurantMenuService.addFoodItem(menuRequest, null);
        });
    }


    @Test
    void testAddFoodItem_ImageProcessingException() throws Exception {
        // Arrange
        RestaurantMenuRequest menuRequest = new RestaurantMenuRequest();
        menuRequest.setItemName("Image Item");
        menuRequest.setCategoryId(1L);
        menuRequest.setRestaurantId(1L);

        MultipartFile foodImage = mock(MultipartFile.class);
        when(foodImage.isEmpty()).thenReturn(false);
        when(foodImage.getBytes()).thenThrow(new IOException("Image processing error"));

        when(restaurantMenuRepository.existsByRestaurantIdAndItemNameIgnoreCase(1L, "Image Item")).thenReturn(false);
        when(foodCategoryRepository.existsById(1L)).thenReturn(true);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            restaurantMenuService.addFoodItem(menuRequest, foodImage);
        });
    }


    @Test
    void deleteFoodItemTest() throws Exception {
        Long itemId = 1L;
        when(restaurantMenuRepository.existsById(itemId)).thenReturn(true);

        restaurantMenuService.deleteFoodItem(itemId);

        verify(restaurantMenuRepository, times(1)).deleteById(itemId);
    }

    @Test
    void deleteFoodItemNotFoundTest() throws Exception {
        Long itemId = 1L;
        when(restaurantMenuRepository.existsById(itemId)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> {
            restaurantMenuService.deleteFoodItem(itemId);
        });

        verify(restaurantMenuRepository, times(1)).existsById(itemId);
        verify(restaurantMenuRepository, never()).deleteById(itemId);
    }

    @Test
    void getFoodItemsByRestaurantIdTest() throws Exception {
        Long restaurantId = 1L;
        List<RestaurantMenu> menus = new ArrayList<>();
        RestaurantMenu menu = new RestaurantMenu();
        menu.setItemId(1L);
        menu.setItemName("Food Item");
        menu.setPrice(12.99);
        menu.setDescription("Description of food item");
        menus.add(menu);

        when(restaurantMenuRepository.findByRestaurantId(restaurantId)).thenReturn(menus);
        when(dtoConversion.convertToRestaurantMenuResponse(menu)).thenReturn(new RestaurantMenuResponse());

        List<RestaurantMenuResponse> responses = restaurantMenuService.getFoodItemsByRestaurantId(restaurantId);

        assertNotNull(responses);
        assertEquals(1, responses.size());
        verify(restaurantMenuRepository, times(1)).findByRestaurantId(restaurantId);
        verify(dtoConversion, times(1)).convertToRestaurantMenuResponse(menu);
    }

    @Test
    void getFoodItemsByRestaurantIdNotFoundTest() throws Exception {
        Long restaurantId = 1L;
        when(restaurantMenuRepository.findByRestaurantId(restaurantId)).thenReturn(new ArrayList<>());

        assertThrows(ResourceNotFoundException.class, () -> {
            restaurantMenuService.getFoodItemsByRestaurantId(restaurantId);
        });

        verify(restaurantMenuRepository, times(1)).findByRestaurantId(restaurantId);
    }
    @Test
    void updateRestaurantMenuNotFoundTest() throws Exception {
        Long restaurantId = 1L;
        RestaurantMenuRequest request = new RestaurantMenuRequest();
        request.setItemName("Food Item");

        when(restaurantMenuRepository.findById(restaurantId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            restaurantMenuService.updateRestaurantMenu(restaurantId, request);
        });

        verify(restaurantMenuRepository, times(1)).findById(restaurantId);
        verify(restaurantMenuRepository, never()).save(any(RestaurantMenu.class));
    }

    @Test
    void getFoodItemByIdSuccessTest() throws Exception {
        Long foodItemId = 1L;
        RestaurantMenu menu = new RestaurantMenu();
        menu.setItemId(foodItemId);
        menu.setItemName("Food Item");

        when(restaurantMenuRepository.findById(foodItemId)).thenReturn(Optional.of(menu));
        RestaurantMenuResponse response = restaurantMenuService.getFoodItemById(foodItemId);

        assertNotNull(response);
        assertEquals(foodItemId, response.getItemId());
        assertEquals("Food Item", response.getItemName());
        verify(restaurantMenuRepository, times(1)).findById(foodItemId);
    }

    @Test
    void findByCategoryIdSuccessTest() throws Exception {
        Long categoryId = 1L;
        List<RestaurantMenu> menus = new ArrayList<>();
        RestaurantMenu menu = new RestaurantMenu();
        menu.setItemId(1L);
        menu.setItemName("Food Item");
        menus.add(menu);

        when(restaurantMenuRepository.findByCategoryId(categoryId)).thenReturn(menus);
        when(dtoConversion.convertToRestaurantMenuResponse(menu)).thenReturn(new RestaurantMenuResponse());

        List<RestaurantMenuResponse> responses = restaurantMenuService.getFoodItemsByCategoryId(categoryId);

        assertNotNull(responses);
        assertEquals(1, responses.size());
        verify(restaurantMenuRepository, times(1)).findByCategoryId(categoryId);
        verify(dtoConversion, times(1)).convertToRestaurantMenuResponse(menu);
    }

    @Test
    void findByCategoryIdNotFoundTest() throws Exception {
        Long categoryId = 1L;
        when(restaurantMenuRepository.findByCategoryId(categoryId)).thenReturn(new ArrayList<>());

        assertThrows(ResourceNotFoundException.class, () -> {
            restaurantMenuService.getFoodItemsByCategoryId(categoryId);
        });

        verify(restaurantMenuRepository, times(1)).findByCategoryId(categoryId);
    }

    @Test
    void getFoodItemsByRestaurantIdExceptionTest() throws Exception {
        Long restaurantId = 1L;
        when(restaurantMenuRepository.findByRestaurantId(restaurantId)).thenThrow(new RuntimeException("Database error"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            restaurantMenuService.getFoodItemsByRestaurantId(restaurantId);
        });

        assertEquals("Database error", thrown.getMessage());
        verify(restaurantMenuRepository, times(1)).findByRestaurantId(restaurantId);
    }

    @Test
    void testUpdateRestaurantMenu_Success() throws ResourceNotFoundException {
        // Arrange
        Long restaurantId = 1L;
        RestaurantMenuRequest menuRequest = new RestaurantMenuRequest();
        menuRequest.setItemName("Updated Food Item");
        menuRequest.setPrice(15.99);
        menuRequest.setDescription("Updated Description");

        RestaurantMenu existingRestaurantMenu = new RestaurantMenu();
        existingRestaurantMenu.setItemId(1L);
        existingRestaurantMenu.setItemName("Old Food Item");
        existingRestaurantMenu.setPrice(12.99);
        existingRestaurantMenu.setDescription("Old Description");

        RestaurantMenu updatedRestaurantMenu = new RestaurantMenu();
        updatedRestaurantMenu.setItemId(1L);
        updatedRestaurantMenu.setItemName("Updated Food Item");
        updatedRestaurantMenu.setPrice(15.99);
        updatedRestaurantMenu.setDescription("Updated Description");

        RestaurantMenuResponse response = new RestaurantMenuResponse();
        response.setItemId(1L);
        response.setItemName("Updated Food Item");
        response.setPrice(15.99);
        response.setDescription("Updated Description");

        // Mocking repository and DTO conversion
        when(restaurantMenuRepository.findById(restaurantId)).thenReturn(Optional.of(existingRestaurantMenu));
        when(restaurantMenuRepository.save(existingRestaurantMenu)).thenReturn(updatedRestaurantMenu);
        when(dtoConversion.convertToRestaurantMenuResponse(updatedRestaurantMenu)).thenReturn(response);

        // Act
        RestaurantMenuResponse result = restaurantMenuService.updateRestaurantMenu(restaurantId, menuRequest);

        // Assert
        assertEquals(response.getItemId(), result.getItemId());
        assertEquals(response.getItemName(), result.getItemName());
        assertEquals(response.getPrice(), result.getPrice());
        assertEquals(response.getDescription(), result.getDescription());

        verify(restaurantMenuRepository, times(1)).findById(restaurantId);
        verify(restaurantMenuRepository, times(1)).save(existingRestaurantMenu);
        verify(dtoConversion, times(1)).convertToRestaurantMenuResponse(updatedRestaurantMenu);
    }

    @Test
    void testUpdateRestaurantMenu_ResourceNotFound() {
        // Arrange
        Long restaurantId = 1L;
        RestaurantMenuRequest menuRequest = new RestaurantMenuRequest();

        when(restaurantMenuRepository.findById(restaurantId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                restaurantMenuService.updateRestaurantMenu(restaurantId, menuRequest)
        );

        assertEquals("FoodItem not found", exception.getMessage());

        verify(restaurantMenuRepository, times(1)).findById(restaurantId);
        verify(restaurantMenuRepository, never()).save(any());
        verify(dtoConversion, never()).convertToRestaurantMenuResponse(any());
    }

}
