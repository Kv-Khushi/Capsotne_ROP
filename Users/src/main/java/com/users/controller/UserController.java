package com.users.controller;

import com.users.constant.ConstantMessage;
import com.users.entities.User;
import com.users.indto.LoginRequest;
import com.users.indto.UserRequest;
import com.users.outdto.UserAddResponse;
import com.users.outdto.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.users.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Controller for handling user-related operations.
 * Provides endpoints for adding, logging in, retrieving, and deleting users.
 */
@RequestMapping("/users")
@RestController
public class UserController {

    /**
     * Logger for logging UserController events.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    /**
     * Service for handling user-related business logic.
     */
    @Autowired
    private UserService userService;

//    @PostMapping("/addUser")
//    public ResponseEntity<UserResponse> addUser(final @Valid @RequestBody UserRequest userRequest) {
//        LOGGER.info("Received request to add a new user with username: {}", userRequest.getUserName());
//
//        UserResponse newUser = userService.addUser(userRequest);
//        LOGGER.info("Successfully added user with username: {}", newUser.getUserName());
//        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
//    }

    @PostMapping("/addUser")
    public ResponseEntity<UserAddResponse> addUser(@Valid @RequestBody UserRequest userRequest) {
        LOGGER.info("Received request to add a new user with username: {}", userRequest.getUserName());

        userService.addUser(userRequest); // Perform the operation
        // Log success message
        LOGGER.info("Successfully added user with username: {}", userRequest.getUserName());

        // Create a response with the success message from ConstantMessage
        UserAddResponse response = new UserAddResponse(ConstantMessage.USER_ADD_SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }



    /**
     * Authenticates a user and logs them in.
     *
     * @param loginRequest the login credentials of the user
     * @return a {@link ResponseEntity} containing the user response with {@link HttpStatus#OK} status
     */
    @PostMapping("/loginUser")
    public ResponseEntity<UserResponse> loginUser(final @Valid @RequestBody LoginRequest loginRequest) {

        UserResponse userResponse = userService.authenticateUser(loginRequest);
        return ResponseEntity.ok(userResponse);
    }

    /**
     * Retrieves all users.
     *
     * @return a {@link ResponseEntity} containing a list of users with {@link HttpStatus#OK} status,
     *         or {@link HttpStatus#NOT_FOUND} if no users are found
     */
    @GetMapping("/getAllUser")
    public ResponseEntity<List<User>> getAllUser() {
        LOGGER.info("Received request to retrieve all users");
        List<User> list = userService.getAllUserList();
        if (!list.isEmpty()) {
            LOGGER.info("Successfully retrieved {} users", list.size());
            return ResponseEntity.ok(list);
        }
        LOGGER.warn("No users found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /**
     * Retrieves a user by user ID.
     *
     * @param userId the ID of the user to be retrieved
     * @return a {@link ResponseEntity} containing the user details with {@link HttpStatus#OK} status,
     *         or {@link HttpStatus#NOT_FOUND} if the user is not found
     */
    @GetMapping("/getUser/{userId}")
    public ResponseEntity<?> getUser(final @PathVariable("userId") Long userId) {
        LOGGER.info("Received request to get user with ID: {}", userId);
        Optional<User> user = userService.getUserById(userId);

        if (user.isPresent()) {
            LOGGER.info("Successfully retrieved user with ID: {}", userId);
            return ResponseEntity.status(HttpStatus.OK).body(user.get());
        } else {
            LOGGER.warn("User not found with ID: {}", userId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}
