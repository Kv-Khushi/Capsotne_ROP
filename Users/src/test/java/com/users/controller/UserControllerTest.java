package com.users.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.users.dto.LoginRequest;
import com.users.dto.UserRequest;
import com.users.dto.UserResponse;
import com.users.entities.User;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.RequestEntity.put;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("User added successfully"));
    }

    @Test
    public void testLoginUser_Success() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setUserEmail("khushi@nucleusteq.com");
        userRequest.setUserPassword("Khushi@123");

        UserResponse userResponse = new UserResponse();
        userResponse.setUserEmail("khushi@nucleusteq.com");

        when(userService.authenticateUser(any(LoginRequest.class))).thenReturn(userResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/loginUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andDo(print())
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
                .andExpect(jsonPath("$[0].status").value(400))
                .andExpect(jsonPath("$[0].message").value("Field 'phoneNumber': Phone number cannot be null"));
    }


    @Test
    public void testAddUser_InvalidEmailFormat() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setPhoneNumber(9587367890L);
        userRequest.setUserName("Khushi");
        userRequest.setUserEmail("invalid-email");
        userRequest.setUserPassword("Khushi@123");
        userRequest.setUserRole(UserRole.CUSTOMER);

        mockMvc.perform(post("/users/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].status").value(400))
                .andExpect(jsonPath("$[0].message").value("Field 'userEmail': Email must end with nucleusteq.com"));
    }

    @Test
    public void testUpdateWalletBalance_Success() throws Exception {
        Long userId = 1L;
        Double newBalance = 1000.0;

        mockMvc.perform(MockMvcRequestBuilders.put("/users/{userId}/wallet", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBalance)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Wallet balance updated successfully."));
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
