package com.users.integration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.users.constant.ConstantMessage;
import com.users.dto.LoginRequest;
import com.users.dto.UserRequest;
import com.users.enums.UserRole;
import com.users.passwordencryption.PasswordEncodingAndDecoding;
import com.users.repository.UserRepository;
import com.users.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
@ActiveProfiles("test")
public class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncodingAndDecoding passwordEncodingAndDecoding;


    @Test
    public void testAddUser_Success() throws Exception {

        UserRequest userRequest = new UserRequest();
        userRequest.setUserEmail("testuser1@nucleusteq.com");
        userRequest.setUserName("Test User");
        userRequest.setPhoneNumber(9876543210L);
        userRequest.setUserPassword("Password@123");
        userRequest.setUserRole(UserRole.CUSTOMER);

        mockMvc.perform(post("/users/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated());

        User savedUser = userRepository.findByUserEmail("testuser1@nucleusteq.com")
                .orElseThrow(() -> new AssertionError("User not found in the database"));

        assertThat(savedUser.getUserName()).isEqualTo("Test User");
        assertThat(savedUser.getPhoneNumber()).isEqualTo(9876543210L);
        assertThat(savedUser.getUserRole()).isEqualTo(UserRole.CUSTOMER);
        assertThat(PasswordEncodingAndDecoding.decodePassword(savedUser.getUserPassword()))
                .isEqualTo("Password@123");
    }


    @Test
    public void testLoginUser_Success() throws Exception {
        String email = "testloginuser@nucleusteq.com";
        String password = "Password@123";
        String encodedPassword = passwordEncodingAndDecoding.encodePassword(password);

        User user = new User();
        user.setUserEmail(email);
        user.setUserName("Test Login User");
        user.setPhoneNumber(9876543210L);
        user.setUserPassword(encodedPassword);
        user.setUserRole(UserRole.CUSTOMER);
        userRepository.save(user);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail(email);
        loginRequest.setUserPassword(password);


        mockMvc.perform(post("/users/loginUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

    }

    @Test
    public void testLoginUser_InvalidPassword() throws Exception {
        String email = "testloginuser@nucleusteq.com";
        String password = "Passsword@123";
        String encodedPassword = passwordEncodingAndDecoding.encodePassword(password);

        User user = new User();
        user.setUserEmail(email);
        user.setUserName("Test Login User");
        user.setPhoneNumber(9876543210L);
        user.setUserPassword(encodedPassword);
        user.setUserRole(UserRole.CUSTOMER);
        userRepository.save(user);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail(email);
        loginRequest.setUserPassword("IncorrectPassword@123");

        mockMvc.perform(post("/users/loginUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());

    }

    @Test
    public void testGetAllUsers_Success() throws Exception {
        User user1 = new User();
        user1.setUserEmail("user1@example.com");
        user1.setUserName("User One");
        user1.setPhoneNumber(1234567890L);
        user1.setUserPassword(passwordEncodingAndDecoding.encodePassword("password1"));
        user1.setUserRole(UserRole.CUSTOMER);
        userRepository.save(user1);

        User user2 = new User();
        user2.setUserEmail("user2@example.com");
        user2.setUserName("User Two");
        user2.setPhoneNumber(9876543210L);
        user2.setUserPassword(passwordEncodingAndDecoding.encodePassword("password2"));
        user2.setUserRole(UserRole.RESTAURANT_OWNER);
        userRepository.save(user2);


        mockMvc.perform(get("/users/getAllUser")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> {

                    String responseContent = result.getResponse().getContentAsString();
                    List<User> userList = objectMapper.readValue(responseContent, List.class);


                    assertThat(userList).hasSize(2);
                    assertThat(userList).extracting("userEmail")
                            .containsExactlyInAnyOrder("user1@example.com", "user2@example.com");
                });

    }

    @Test
    public void testGetUserById_Success() throws Exception {
        // Create and save a user to the database
        User user = new User();
        user.setUserEmail("user@test.com");
        user.setUserName("Test User");
        user.setPhoneNumber(9876543210L);
        user.setUserPassword("Password123");
        user.setUserRole(UserRole.CUSTOMER);

        user = userRepository.save(user);
        Long userId = user.getUserId();

        // Perform the GET request to retrieve the user by ID
        mockMvc.perform(get("/users/getUser/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    String response = result.getResponse().getContentAsString();
                    User responseUser = objectMapper.readValue(response, User.class);
                    assertThat(responseUser.getUserId()).isEqualTo(userId);
                    assertThat(responseUser.getUserName()).isEqualTo("Test User");
                    assertThat(responseUser.getUserEmail()).isEqualTo("user@test.com");
                });
    }

    @Test
    public void testGetUserById_NotFound() throws Exception {
        Long nonExistentUserId = 999L;

        // Perform the GET request for a user ID that does not exist
        mockMvc.perform(get("/users/getUser/{userId}", nonExistentUserId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(ConstantMessage.NOT_FOUND));
    }


    @Test
    public void testUpdateWalletBalance_Success() throws Exception {
        // Create and save a CUSTOMER user
        User user = new User();
        user.setUserEmail("customer@test.com");
        user.setUserName("Test Customer");
        user.setPhoneNumber(9876543210L);
        user.setUserPassword("Password123");
        user.setUserRole(UserRole.CUSTOMER);
        user.setWallet(100.0);

        user = userRepository.save(user);
        Long userId = user.getUserId();
        Double newBalance = 200.0;

        // Perform the PUT request to update the wallet balance
        mockMvc.perform(put("/users/{userId}/wallet", userId)
                        .param("newBalance", newBalance.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(ConstantMessage.UPDATED_WALLET_BALANCE));

        // Verify the wallet balance has been updated
        Optional<User> updatedUser = userRepository.findById(userId);
        assertThat(updatedUser).isPresent();
        assertThat(updatedUser.get().getWallet()).isEqualTo(newBalance);
    }

    @Test
    public void testUpdateWalletBalance_UserNotFound() throws Exception {
        Long nonExistentUserId = 999L;
        Double newBalance = 200.0;

        // Perform the PUT request for a user ID that does not exist
        mockMvc.perform(put("/users/{userId}/wallet", nonExistentUserId)
                        .param("newBalance", newBalance.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"status\":404,\"message\":\"User not found\"}"));
    }

    @Test
    public void testUpdateWalletBalance_InvalidRole() throws Exception {
        // Create and save a RESTAURANT_OWNER user
        User user = new User();
        user.setUserEmail("owner@test.com");
        user.setUserName("Test Owner");
        user.setPhoneNumber(9876543210L);
        user.setUserPassword("Password123");
        user.setUserRole(UserRole.RESTAURANT_OWNER);
        user.setWallet(300.0);

        user = userRepository.save(user);
        Long userId = user.getUserId();
        Double newBalance = 500.0;

        // Perform the PUT request and expect an InvalidRequestException
        mockMvc.perform(put("/users/{userId}/wallet", userId)
                        .param("newBalance", newBalance.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Restaurant Owner can not able to add amount in wallet")));
    }

}


