package com.users.service;

import com.users.entities.User;
import com.users.exception.AlreadyExists;
import com.users.exception.NotFoundException;
import com.users.indto.LoginRequest;
import com.users.indto.UserRequest;
import com.users.outdto.UserResponse;
import com.users.repository.UserRepository;
import com.users.passwordencryption.PasswordEncodingAndDecoding;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddUser_Success() {
        // Arrange
        UserRequest userRequest = new UserRequest();
        userRequest.setUserEmail("khushi@gmail.com");
        userRequest.setUserPassword("password");

        // Create a User object with encoded password
        User user = new User();
        user.setUserEmail("khushi@gmail.com");
        user.setUserPassword(PasswordEncodingAndDecoding.encodePassword("password"));

        UserResponse userResponse = new UserResponse();

        // Mock repository behavior
        when(userRepository.findByUserEmail(userRequest.getUserEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        UserResponse response = userService.addUser(userRequest);

        // Assert
        assertNotNull(response);
        assertEquals("khushi@gmail.com", response.getUserEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }


    @Test
    public void testAddUser_UserAlreadyExists() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUserEmail("Khushi@gmail.com");

        when(userRepository.findByUserEmail(userRequest.getUserEmail())).thenReturn(Optional.of(new User()));

        assertThrows(AlreadyExists.class, () -> userService.addUser(userRequest));
    }


    @Test
    public void testAuthenticateUser_Success() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail("khushi@gmail.com");
        loginRequest.setUserPassword("password");

        // Create a User object with encoded password
        User user = new User();
        user.setUserEmail("khushi@gmail.com");
        user.setUserPassword(PasswordEncodingAndDecoding.encodePassword("password"));

        UserResponse userResponse = new UserResponse();
        userResponse.setUserEmail("khushi@gmail.com");

        // Mocking the repository
        when(userRepository.findByUserEmail(loginRequest.getUserEmail())).thenReturn(Optional.of(user));

        // Act
        UserResponse response = userService.authenticateUser(loginRequest);

        // Assert
        assertNotNull(response);
        assertEquals("khushi@gmail.com", response.getUserEmail());
        verify(userRepository, times(1)).findByUserEmail(loginRequest.getUserEmail());
    }


    @Test
    public void testAuthenticateUser_NotFound() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail("khushi@gmail.com");

        when(userRepository.findByUserEmail(loginRequest.getUserEmail())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.authenticateUser(loginRequest));
    }

}

