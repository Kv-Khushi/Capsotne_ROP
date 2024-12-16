package com.users.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.users.constant.ConstantMessage;
import com.users.dto.LoginRequest;
import com.users.dto.UserRequest;
import com.users.enums.UserRole;
import com.users.entities.User;
import com.users.passwordencryption.PasswordEncodingAndDecoding;
import com.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

  @Autowired
    private MockMvc mockMvc;

  @Autowired
    private ObjectMapper objectMapper;

  @Autowired
    private UserRepository userRepository;

  private UserRequest userRequest;

  private LoginRequest loginRequest;
  private User user;

  private PasswordEncodingAndDecoding passwordEncodingAndDecoding;


  @BeforeEach
    public void setup(){
      userRepository.deleteAll();

      passwordEncodingAndDecoding = new PasswordEncodingAndDecoding();


      //Creating user request
      userRequest= new UserRequest();
      userRequest.setUserName("Test_User");
      userRequest.setUserEmail("testuser@nucleusteq.com");
      userRequest.setPhoneNumber(9989322112L);
      userRequest.setUserPassword("Password@123");
      userRequest.setUserRole(UserRole.CUSTOMER);

      //create and save entity
      user = new User();
      user.setUserName("Test_User");
      user.setUserEmail("testuser@nucleusteq.com");
      user.setPhoneNumber(9989322112L);
      user.setUserPassword(passwordEncodingAndDecoding.encodePassword("Password@123"));
      user.setUserRole(UserRole.CUSTOMER);

      userRepository.save(user);

      loginRequest = new LoginRequest();
      loginRequest.setUserEmail("testuser@nucleusteq.com");
      loginRequest.setUserPassword("Password@123");
  }

    @Test
    public void testAddUserIntegration() throws Exception {
        userRepository.deleteAll();
        String userJson = objectMapper.writeValueAsString(userRequest);

        // Perform the POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/users/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(ConstantMessage.USER_ADD_SUCCESS));

        // Check if user is saved in the database
        Optional<User> savedUser = userRepository.findByUserEmail("testuser@nucleusteq.com");
        assertTrue(savedUser.isPresent());
        assertEquals("Test_User", savedUser.get().getUserName());
        assertEquals("testuser@nucleusteq.com", savedUser.get().getUserEmail());
    }


    @Test
    public void testLoginUserSuccess() throws Exception {
        String loginJson = objectMapper.writeValueAsString(loginRequest);

        mockMvc.perform(post("/users/loginUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("Test_User"))
                .andExpect(jsonPath("$.userEmail").value("testuser@nucleusteq.com"));
    }
}
