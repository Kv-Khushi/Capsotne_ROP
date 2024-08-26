package com.users.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

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
import org.springframework.test.web.servlet.MockMvc;

import com.users.enums.UserRole;

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
        userRequest.setUserEmail("khushi@gmail.com");
        userRequest.setUserPassword("khushi123");
        userRequest.setUserRole(UserRole.CUSTOMER);

        UserResponse userResponse = new UserResponse();
        userResponse.setUserEmail("khushi@gmail.com");

        when(userService.addUser(any(UserRequest.class))).thenReturn(userResponse);

        mockMvc.perform(post("/users/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userEmail").value("khushi@gmail.com"));
    }

    @Test
    public void testAddUser_ValidationError() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setUserEmail("khushi@gmail.com");
        userRequest.setUserPassword("khushi123");

        mockMvc.perform(post("/users/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Validation failed: phone number can not be null")); // Update this to match the actual message
    }

    @Test
    public void testLoginUser_Success() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setUserEmail("khushi@gmail.com");
        userRequest.setUserPassword("khushi123");

        UserResponse userResponse = new UserResponse();
        userResponse.setUserEmail("khushi@gmail.com");

        when(userService.authenticateUser(any(com.users.indto.LoginRequest.class))).thenReturn(userResponse);

        mockMvc.perform(post("/users/loginUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userEmail").value("khushi@gmail.com"));
    }


    @Test
    public void testDeleteUser_Success() throws Exception {
        mockMvc.perform(delete("/users/deleteUser/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}
