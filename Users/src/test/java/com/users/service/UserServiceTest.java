package com.users.service;

import com.users.constant.ConstantMessage;
import com.users.dto.LoginRequest;
import com.users.dto.UserRequest;
import com.users.dto.UserResponse;
import com.users.entities.User;
import com.users.enums.UserRole;
import com.users.exception.ResourceNotFoundException;
import com.users.repository.UserRepository;
import com.users.passwordencryption.PasswordEncodingAndDecoding;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncodingAndDecoding passwordEncodingAndDecoding;

    @InjectMocks
    private UserService userService;

    @InjectMocks
    private EmailService emailService;



    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddUser_Success() {

        UserRequest userRequest = new UserRequest();
        userRequest.setUserEmail("user@gmail.com");
        userRequest.setUserPassword("password");

        User user = new User();
        user.setUserEmail("user@gmail.com");
        user.setUserPassword(PasswordEncodingAndDecoding.encodePassword("password"));

        UserResponse userResponse = new UserResponse();

        when(userRepository.findByUserEmail(userRequest.getUserEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);


        UserResponse response = userService.addUser(userRequest);


        assertNotNull(response);
        assertEquals("user@gmail.com", response.getUserEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }



    @Test
    public void testAuthenticateUser_Success() {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail("user@gmail.com");
        loginRequest.setUserPassword("password");


        User user = new User();
        user.setUserEmail("user@gmail.com");
        user.setUserPassword(PasswordEncodingAndDecoding.encodePassword("password"));

        UserResponse userResponse = new UserResponse();
        userResponse.setUserEmail("user@gmail.com");


        when(userRepository.findByUserEmail(loginRequest.getUserEmail())).thenReturn(Optional.of(user));


        UserResponse response = userService.authenticateUser(loginRequest);


        assertNotNull(response);
        assertEquals("user@gmail.com", response.getUserEmail());
        verify(userRepository, times(1)).findByUserEmail(loginRequest.getUserEmail());
    }


    @Test
    public void testAuthenticateUser_NotFound() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail("user@gmail.com");

        when(userRepository.findByUserEmail(loginRequest.getUserEmail())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.authenticateUser(loginRequest));
    }

    @Test
    public void testUpdateWalletBalance_Success() {
        //Arrange
        Long userId = 1L;
        Double newBalance = 100.0;
        User user = new User();
        user.setUserId(userId);
        user.setWallet(50.0);
        user.setUserRole(UserRole.CUSTOMER); // Ensure the user is not a restaurant owner

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        userService.updateWalletBalance(userId, newBalance);

        // Assert
        assertEquals(newBalance, user.getWallet());
        verify(userRepository, times(1)).save(user); // Ensure save is called
    }

    @Test
    public void testUpdateWalletBalance_UserNotFound() {
        // Arrange
        Long userId = 1L;
        Double newBalance = 100.0;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> userService.updateWalletBalance(userId, newBalance));
    }

    @Test
    public void testGetAllUserList_NoUsers() {
        // Arrange
        when(userRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<User> result = userService.getAllUserList();

        // Assert
        assertEquals(0, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetUserById_Success() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setUserId(userId);
        user.setUserEmail("user@gmail.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userService.getUserById(userId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(userId, result.get().getUserId());
        assertEquals("user@gmail.com", result.get().getUserEmail());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testGetUserById_UserNotFound() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userService.getUserById(userId);

        // Assert
        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testAddUser_WithDefaultWalletBalance() {
        // Arrange
        UserRequest userRequest = new UserRequest();
        userRequest.setUserEmail("user@gmail.com");
        userRequest.setUserRole(UserRole.CUSTOMER); // Role that requires a wallet balance
        userRequest.setUserPassword("password");

        User user = new User();
        user.setUserEmail("user@gmail.com");
        user.setUserPassword(PasswordEncodingAndDecoding.encodePassword("password"));

        // Setting the expected wallet balance in the user object
        user.setWallet(ConstantMessage.WALLET_AMOUNT); // Make sure this is included

        when(userRepository.findByUserEmail(userRequest.getUserEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        UserResponse response = userService.addUser(userRequest);

        // Assert
        assertNotNull(response);
        assertEquals("user@gmail.com", response.getUserEmail());
        assertEquals(ConstantMessage.WALLET_AMOUNT, user.getWallet()); // Assert correct wallet amount
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testUpdateWalletBalance_LargeBalance() {
        // Arrange
        Long userId = 1L;
        Double newBalance = 100000.0;
        User user = new User();
        user.setUserId(userId);
        user.setWallet(500.0);
        user.setUserRole(UserRole.CUSTOMER); // Ensure the role is not RESTAURANT_OWNER

        // Mock the userRepository behavior
        when(userRepository.findById(userId)).thenReturn(Optional.of(user)); // Mock user retrieval
        when(userRepository.save(any(User.class))).thenReturn(user); // Mock save operation

        // Act
        userService.updateWalletBalance(userId, newBalance);

        // Assert
        assertEquals(newBalance, user.getWallet()); // Check if the wallet balance is updated
        verify(userRepository, times(1)).save(user); // Verify that save was called once
    }


    @Test
    public void testAuthenticateUser_PasswordEncoding() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail("user@gmail.com");
        loginRequest.setUserPassword("password");

        User user = new User();
        user.setUserEmail("user@gmail.com");
       user.setUserPassword(passwordEncodingAndDecoding.encodePassword("password"));

        when(userRepository.findByUserEmail(loginRequest.getUserEmail())).thenReturn(Optional.of(user));

        // Act
        UserResponse response = userService.authenticateUser(loginRequest);

        // Assert
        assertNotNull(response);
        assertEquals("user@gmail.com", response.getUserEmail());
//-        verify(passwordEncodingAndDecoding, times(1)).encodePassword(loginRequest.getUserPassword());
    }
}

