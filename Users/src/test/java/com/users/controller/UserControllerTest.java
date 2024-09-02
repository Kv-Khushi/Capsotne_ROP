package com.users.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.users.entities.User;
import com.users.indto.UserRequest;
import com.users.outdto.UserResponse;
import com.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.test.web.servlet.MockMvc;

import com.users.enums.UserRole;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
        userRequest.setPhoneNumber(1234567890L);
        userRequest.setUserName("Khushi");
        userRequest.setUserEmail("khushi@nucleusteq.com");
        userRequest.setUserPassword("Khushi@123");
        userRequest.setUserRole(UserRole.CUSTOMER);

        UserResponse userResponse = new UserResponse();
        userResponse.setUserEmail("khushi@nucleusteq.com");

        when(userService.addUser(any(UserRequest.class))).thenReturn(userResponse);

        mockMvc.perform(post("/users/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userEmail").value("khushi@nucleusteq.com"));
    }

    @Test
    public void testLoginUser_Success() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setUserEmail("khushi@nucleusteq.com");
        userRequest.setUserPassword("Khushi@123");

        UserResponse userResponse = new UserResponse();
        userResponse.setUserEmail("khushi@nucleusteq.com");

        when(userService.authenticateUser(any(com.users.indto.LoginRequest.class))).thenReturn(userResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/loginUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userEmail").value("khushi@nucleusteq.com"));
    }


    @Test
    public void testAddUser_PhoneNumberNullValidationError() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setUserEmail("khushi@nucleusteq.com");
        userRequest.setUserPassword("Khushi@123");
        userRequest.setUserId(1L);
        userRequest.setUserRole(UserRole.CUSTOMER);
        userRequest.setPhoneNumber(null);  // Set to null to trigger @NotNull validation
        userRequest.setUserName("Khushi Vyas");

        mockMvc.perform(post("/users/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Validation failed: Phone number cannot be null"));
    }





}
