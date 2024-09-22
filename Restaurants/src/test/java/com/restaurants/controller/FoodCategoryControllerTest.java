package com.restaurants.controller;

import com.restaurants.dto.FoodCategoryRequest;
import com.restaurants.dto.FoodCategoryResponse;
import com.restaurants.dto.SuccessResponse;
import com.restaurants.service.FoodCategoryService;
import com.restaurants.exception.ResourceNotFoundException;
import com.restaurants.constant.ConstantMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


class FoodCategoryControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private FoodCategoryController controller;

    @Mock
    private FoodCategoryService service;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testAddFoodCategory_Success() {
        // Arrange
        FoodCategoryRequest foodCategoryRequest = new FoodCategoryRequest();
        foodCategoryRequest.setCategoryName("Appetizers");

        // Act
        ResponseEntity<SuccessResponse> responseEntity = controller.addFoodCategory(foodCategoryRequest);

        // Assert
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(ConstantMessage.CATEGORY_ADD_SUCCESS, responseEntity.getBody().getMessage());

        // Verify that the service method was called
        verify(service, times(1)).addFoodCategory(foodCategoryRequest);
    }


    @Test
    void deleteCategoryTest() throws Exception {
        doNothing().when(service).deleteFoodCategory(anyLong());

        mockMvc.perform(delete("/foodCategories/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Food category deleted successfully."));

        verify(service, times(1)).deleteFoodCategory(1L);
    }


    @Test
    void getCategoriesByRestaurantIdTest() throws Exception {
        FoodCategoryResponse res = new FoodCategoryResponse();
        res.setCategoryId(1L);
        res.setRestaurantId(1L);
        res.setCategoryName("Appetizers");

        when(service.getAllCategoriesByRestaurantId(anyLong())).thenReturn(Arrays.asList(res));

        mockMvc.perform(get("/foodCategories/restaurant/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].categoryId").value(1L))
                .andExpect(jsonPath("$[0].restaurantId").value(1L))
                .andExpect(jsonPath("$[0].categoryName").value("Appetizers"));

        verify(service, times(1)).getAllCategoriesByRestaurantId(1L);
    }

    @Test
    void updateCategoryTest() throws Exception {
        FoodCategoryResponse res = new FoodCategoryResponse();
        res.setCategoryId(1L);
        res.setRestaurantId(1L);
        res.setCategoryName("Main Course");

        when(service.updateCategoryName(anyLong(), anyString())).thenReturn(res);

        mockMvc.perform(put("/foodCategories/1/name")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("Main Course"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryId").value(1L))
                .andExpect(jsonPath("$.restaurantId").value(1L))
                .andExpect(jsonPath("$.categoryName").value("Main Course"));

        verify(service, times(1)).updateCategoryName(1L, "Main Course");
    }


}
