package com.users.controller;

import com.users.entities.User;
import com.users.indto.LoginRequest;
import com.users.indto.UserRequest;
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
import org.springframework.web.bind.annotation.DeleteMapping;

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

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * Adds a new user.
     *
     * @param userRequest the details of the user to be added
     * @return a {@link ResponseEntity} containing the user response with {@link HttpStatus#CREATED} status
     */
    @PostMapping("/addUser")
    public ResponseEntity<UserResponse> addUser(@Valid @RequestBody UserRequest userRequest){

        logger.info("Received request to add a new user with username: {}", userRequest.getUserName());

        UserResponse newUser = userService.addUser(userRequest);
        if (newUser != null){
            logger.info("Successfully added user with username: {}", newUser.getUserName());
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        }
        logger.error("Failed to add user with username: {}", userRequest.getUserName());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * Authenticates a user and logs them in.
     *
     * @param loginRequest the login credentials of the user
     * @return a {@link ResponseEntity} containing the user response with {@link HttpStatus#OK} status
     */
    @PostMapping("/loginUser")
    public ResponseEntity<UserResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest){

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
    public ResponseEntity<List<User>> getAllUser(){
        logger.info("Received request to retrieve all users");
        List<User> list = userService.getAllUserList();
        if (!list.isEmpty()) {
            logger.info("Successfully retrieved {} users", list.size());
            return ResponseEntity.ok(list);
        }
        logger.warn("No users found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /**
     * Deletes a user by user ID.
     *
     * @param userId the ID of the user to be deleted
     * @return a {@link ResponseEntity} with {@link HttpStatus#NO_CONTENT} status if deletion is successful,
     *         or {@link HttpStatus#NOT_FOUND} if the user to be deleted is not found
     */
    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<String> deleteUser(final @PathVariable("userId") Long userId) {
        logger.info("Received request to delete user with ID: {}", userId);
        boolean isDeleted = userService.deleteUser(userId);
        if (isDeleted) {
            logger.info("Successfully deleted user with ID : {}", userId);
            return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
        }
        else {
            logger.warn("User not found with ID: {}", userId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

    }

    @GetMapping("/getUser/{userId}")
    public ResponseEntity<?> getUser(@PathVariable("userId") Long userId) {
        logger.info("Received request to get user with ID: {}", userId);
        Optional<User> user = userService.getUserById(userId);

        if (user.isPresent()) {
            logger.info("Successfully retrieved user with ID: {}", userId);
            return ResponseEntity.status(HttpStatus.OK).body(user.get());
        } else {
            logger.warn("User not found with ID: {}", userId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

}
