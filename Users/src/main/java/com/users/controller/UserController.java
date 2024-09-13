package com.users.controller;

import com.users.constant.ConstantMessage;
import com.users.entities.User;
import com.users.dto.LoginRequest;
import com.users.dto.UserRequest;
import com.users.dto.CommonResponse;
import com.users.dto.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.users.service.UserService;
import org.springframework.web.bind.annotation.*;


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


    /**
     * Adds a new user.
     * This method is not designed to be overridden.
     *
     * @param userRequest the request object containing user details
     * @return a {@link ResponseEntity} with a success message
     */

    @PostMapping("/addUser")
    public final ResponseEntity<CommonResponse> addUser(final @Valid @RequestBody UserRequest userRequest) {
        LOGGER.info("Received request to add a new user with username: {}", userRequest.getUserName());

        userService.addUser(userRequest); // Perform the operation
        // Log success message
        LOGGER.info("Successfully added user with username: {}", userRequest.getUserName());

        // Create a response with the success message from ConstantMessage
        CommonResponse response = new CommonResponse(ConstantMessage.USER_ADD_SUCCESS);
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


    @PutMapping("/{userId}/wallet")
    public ResponseEntity<String> updateWalletBalance(@PathVariable Long userId, @RequestBody Double newBalance) {
        userService.updateWalletBalance(userId, newBalance);  // Call the service method
        return ResponseEntity.ok("Wallet balance updated successfully.");
    }


    @PostMapping("/send")
    public ResponseEntity<?> sendEmail(@RequestParam String text) {
        userService.sendMail(text);
        return new ResponseEntity<>(ConstantMessage.MAIL_SENT_SUCCESSFULLY, HttpStatus.OK);
    }

}
