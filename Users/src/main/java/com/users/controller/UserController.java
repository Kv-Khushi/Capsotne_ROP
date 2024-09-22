package com.users.controller;

import com.users.constant.ConstantMessage;
import com.users.dto.*;
import com.users.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.users.service.UserService;
import org.springframework.validation.annotation.Validated;
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
@Slf4j
public class UserController{
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
        log.info("Received request to add a new user with username: {}", userRequest.getUserName());

        userService.addUser(userRequest);

        log.info("Successfully added user with username: {}", userRequest.getUserName());

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
        log.info("Received request to retrieve all users");
        List<User> list = userService.getAllUserList();
         return ResponseEntity.ok(list);
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
        log.info("Received request to get user with ID: {}", userId);
        Optional<User> user = userService.getUserById(userId);
        if (user.isPresent()) {
            log.info("Successfully retrieved user with ID: {}", userId);
            return ResponseEntity.status(HttpStatus.OK).body(user.get());
        } else {
            log.error("User not found with ID: {}", userId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ConstantMessage.NOT_FOUND);
        }
    }

    /**
     * Updates the wallet balance of a user.
     * This method is not designed to be overridden.
     *
     * @param userId     the ID of the user whose wallet balance needs to be updated
     * @param newBalance the new balance to be set
     * @return a {@link ResponseEntity} containing a success message
     */

    @PutMapping("/{userId}/wallet")
    public ResponseEntity<String> updateWalletBalance(@PathVariable final Long userId, @RequestParam final Double newBalance) {
        userService.updateWalletBalance(userId, newBalance);
        return ResponseEntity.status(HttpStatus.OK).body(ConstantMessage.UPDATED_WALLET_BALANCE);
    }

    /**
     * Sends an email with the provided text.
     * <p>
     * This method processes the {@code contactUsRequest} and sends an email based on
     * the details provided in the request. It uses the {@link UserService} to handle
     * the email sending operation.
     * </p>
     *
     * @param contactUsRequest the request containing the details for sending the email
     * @return a {@link ResponseEntity} containing a {@link CommonResponse} with a success message
     */
    @PostMapping("/sendEmail")
    public ResponseEntity<CommonResponse> sendContactUsEmail(final @RequestBody @Validated ContactUsRequest contactUsRequest){
       CommonResponse response = userService.sendContactUsEmail(contactUsRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
