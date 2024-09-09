package com.restaurants.service;

import com.restaurants.dtoconversion.DtoConversion;
import com.restaurants.entities.RestaurantMenu;
import com.restaurants.exception.NotFoundException;
import com.restaurants.dto.indto.RestaurantMenuRequest;
import com.restaurants.dto.outdto.RestaurantMenuResponse;
import com.restaurants.repository.RestaurantMenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

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

    @InjectMocks
    private RestaurantMenuService restaurantMenuService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addFoodItemTest() throws Exception {
        RestaurantMenuRequest request = new RestaurantMenuRequest();
        request.setItemName("Pizza");
        request.setPrice(12.99);
        request.setDescription("Delicious cheese pizza");
        request.setVegNonVeg(Boolean.TRUE);
        request.setCategoryId(1L);
        request.setRestaurantId(1L);

        RestaurantMenu menu = new RestaurantMenu();
        menu.setItemId(1L);
        menu.setItemName("Pizza");
        menu.setPrice(12.99);
        menu.setDescription("Delicious cheese pizza");
        menu.setVegNonVeg(Boolean.TRUE);
        menu.setCategoryId(1L);
        menu.setRestaurantId(1L);

        when(dtoConversion.convertToRestaurantMenuEntity(request)).thenReturn(menu);
        when(restaurantMenuRepository.save(menu)).thenReturn(menu);
        when(dtoConversion.convertToRestaurantMenuResponse(menu)).thenReturn(new RestaurantMenuResponse());

        MultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", new byte[0]);

        RestaurantMenuResponse response = restaurantMenuService.addFoodItem(request, file);

        assertNotNull(response);
        verify(dtoConversion, times(1)).convertToRestaurantMenuEntity(request);
        verify(restaurantMenuRepository, times(1)).save(menu);
        verify(dtoConversion, times(1)).convertToRestaurantMenuResponse(menu);
    }

    @Test
    void addFoodItemMultipartFileNullTest() throws Exception {
        RestaurantMenuRequest request = new RestaurantMenuRequest();
        request.setItemName("Pizza");
        request.setPrice(12.99);
        request.setDescription("Delicious cheese pizza");
        request.setVegNonVeg(Boolean.TRUE);
        request.setCategoryId(1L);
        request.setRestaurantId(1L);

        RestaurantMenu menu = new RestaurantMenu();
        menu.setItemId(1L);
        menu.setItemName("Pizza");
        menu.setPrice(12.99);
        menu.setDescription("Delicious cheese pizza");
        menu.setVegNonVeg(Boolean.TRUE);
        menu.setCategoryId(1L);
        menu.setRestaurantId(1L);

        when(dtoConversion.convertToRestaurantMenuEntity(request)).thenReturn(menu);
        when(restaurantMenuRepository.save(menu)).thenReturn(menu);
        when(dtoConversion.convertToRestaurantMenuResponse(menu)).thenReturn(new RestaurantMenuResponse());

        RestaurantMenuResponse response = restaurantMenuService.addFoodItem(request, null);

        assertNotNull(response);
        verify(dtoConversion, times(1)).convertToRestaurantMenuEntity(request);
        verify(restaurantMenuRepository, times(1)).save(menu);
        verify(dtoConversion, times(1)).convertToRestaurantMenuResponse(menu);
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

        assertThrows(NotFoundException.class, () -> {
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
        menu.setItemName("Pizza");
        menu.setPrice(12.99);
        menu.setDescription("Delicious cheese pizza");
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

        assertThrows(NotFoundException.class, () -> {
            restaurantMenuService.getFoodItemsByRestaurantId(restaurantId);
        });

        verify(restaurantMenuRepository, times(1)).findByRestaurantId(restaurantId);
    }
    @Test
    void updateRestaurantMenuNotFoundTest() throws Exception {
        Long restaurantId = 1L;
        RestaurantMenuRequest request = new RestaurantMenuRequest();
        request.setItemName("Pizza");

        when(restaurantMenuRepository.findById(restaurantId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            restaurantMenuService.updateRestaurantMenu(restaurantId, request);
        });

        verify(restaurantMenuRepository, times(1)).findById(restaurantId);
        verify(restaurantMenuRepository, never()).save(any(RestaurantMenu.class));
    }


}
