package com.users.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.users.constant.ConstantMessage;
import com.users.dto.AddressRequest;
import com.users.dto.AddressResponse;
import com.users.dto.CommonResponse;
import com.users.entities.Address;
import com.users.service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AddressController.class)
public class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService addressService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void addAddress_shouldReturnCreatedCommonResponse() throws Exception {
        // Given
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setStreet("123 Main St");
        addressRequest.setCity("Indore");
        addressRequest.setState("MP");
        addressRequest.setZipCode(12345);
        addressRequest.setCountry("India");
        addressRequest.setUserId(1L);

        CommonResponse commonResponse = new CommonResponse(ConstantMessage.ADDRESS_ADD_SUCCESS);

        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/address/addAddress")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addressRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(commonResponse)));
    }




    @Test
    void deleteAddress_shouldReturnNoContent() throws Exception {
        // Given
        Long userId = 1L;
        when(addressService.deleteAddress(userId)).thenReturn(true);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/address/deleteAddress/{userId}", userId))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteAddress_shouldReturnNotFound() throws Exception {
        // Given
        Long userId = 1L;
        when(addressService.deleteAddress(userId)).thenReturn(false);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/address/deleteAddress/{userId}", userId))
                .andExpect(status().isNotFound());
    }


    @Test
    void getAllAddressForUserFind_shouldReturnListOfAddresses() throws Exception {
        // Given
        Long userId = 1L;
        Address address1 = new Address();
        Address address2 = new Address();
        List<Address> addresses = Arrays.asList(address1, address2);
        when(addressService.getAllAddressForUser(userId)).thenReturn(addresses);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/address/getAddress/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(addresses)));
    }



}

