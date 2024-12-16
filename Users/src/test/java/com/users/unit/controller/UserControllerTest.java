package com.users.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.users.constant.ConstantMessage;
import com.users.controller.UserController;
import com.users.dto.LoginRequest;
import com.users.dto.UserRequest;
import com.users.dto.UserResponse;
import com.users.entities.User;
import com.users.exception.InvalidRequestException;
import com.users.exception.ResourceNotFoundException;
import com.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.users.enums.UserRole;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;




@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddUser_Success() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setPhoneNumber(7894567890L);
        userRequest.setUserName("test");
        userRequest.setUserEmail("test@nucleusteq.com");
        userRequest.setUserPassword("Test@123");
        userRequest.setUserRole(UserRole.CUSTOMER);

        UserResponse userResponse = new UserResponse();
        userResponse.setUserEmail("test@nucleusteq.com");

        when(userService.addUser(any(UserRequest.class))).thenReturn(userResponse);

        mockMvc.perform(post("/users/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("User added successfully"));
    }

    @Test
    public void testLoginUser_Success() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setUserEmail("test@nucleusteq.com");
        userRequest.setUserPassword("Test@123");

        UserResponse userResponse = new UserResponse();
        userResponse.setUserEmail("test@nucleusteq.com");

        when(userService.authenticateUser(any(LoginRequest.class))).thenReturn(userResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/loginUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userEmail").value("test@nucleusteq.com"));
    }


    @Test
    public void testAddUser_PhoneNumberNullValidationError() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setUserEmail("test@nucleusteq.com");
        userRequest.setUserPassword("Test@123");
        userRequest.setUserId(1L);
        userRequest.setUserRole(UserRole.CUSTOMER);
        userRequest.setPhoneNumber(null);
        userRequest.setUserName("Test");

        mockMvc.perform(post("/users/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].status").value(400))
                .andExpect(jsonPath("$[0].message").value("Field 'phoneNumber': Phone number cannot be null"));
    }



    @Test
    public void testUpdateWalletBalance_Success() throws Exception {
        doNothing().when(userService).updateWalletBalance(1L, 10.0); // Change the value to test

        mockMvc.perform(put("/users/1/wallet")
                        .param("newBalance", "10") // Correctly use param for query parameters
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(ConstantMessage.UPDATED_WALLET_BALANCE));
    }

    @Test
    public void testUpdateWalletBalance_UserNotFound() throws Exception {
        doThrow(new ResourceNotFoundException(ConstantMessage.NOT_FOUND))
                .when(userService).updateWalletBalance(1L, 10.0);

        mockMvc.perform(put("/users/1/wallet")
                        .param("newBalance", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // Expect 404 Not Found
    }

    @Test
    public void testUpdateWalletBalance_UserIsRestaurantOwner() throws Exception {
        doThrow(new InvalidRequestException(ConstantMessage.OWNER_CAN_N0T_UPDATE_WALLET))
                .when(userService).updateWalletBalance(1L, 10.0);

        mockMvc.perform(put("/users/1/wallet")
                        .param("newBalance", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()); // Expect 400 Bad Request
    }

    @Test
    public void testUpdateWalletBalance_InvalidBalance() throws Exception {
        // Assuming your service method throws an exception for invalid balance values
        doThrow(new InvalidRequestException("Invalid balance amount"))
                .when(userService).updateWalletBalance(1L, -10.0); // Negative balance

        mockMvc.perform(put("/users/1/wallet")
                        .param("newBalance", "-10") // Test with an invalid balance
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()); // Expect 400 Bad Request
    }



    @Test
    public void testGetUser_Success() throws Exception {
        Long userId = 1L;
        User user = new User();
        user.setUserId(userId);
        user.setUserEmail("test@example.com");

        when(userService.getUserById(userId)).thenReturn(Optional.of(user));

        mockMvc.perform(MockMvcRequestBuilders.get("/users/getUser/{userId}", userId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userEmail").value("test@example.com"));
    }

    @Test
    public void testGetAllUser_Success() throws Exception {
        User user1 = new User();
        User user2 = new User();
        List<User> userList = Arrays.asList(user1, user2);

        when(userService.getAllUserList()).thenReturn(userList);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/users/getAllUser"))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String expectedResponse = objectMapper.writeValueAsString(userList);

        assertEquals(expectedResponse, responseBody);
    }

    @Test
    public void testGetUser_UserNotFound() throws Exception {
        Long userId = 1L;

        when(userService.getUserById(userId)).thenReturn(Optional.empty());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/users/getUser/{userId}", userId))
                .andExpect(status().isNotFound())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertEquals("User not found", responseBody);
    }

}
