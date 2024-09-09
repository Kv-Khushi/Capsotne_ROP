package com.users.service;

import com.users.constant.ConstantMessage;
import com.users.dtoconversion.DtoConversion;
import com.users.entities.User;
import com.users.exception.AlreadyExists;
import com.users.exception.NotFoundException;
import com.users.indto.LoginRequest;
import com.users.indto.UserRequest;
import com.users.outdto.UserResponse;
import com.users.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.users.passwordencryption.PasswordEncodingAndDecoding;

import java.util.List;
import java.util.Optional;

/**
 * Service class for handling user-related operations.
 */
@Service
public class UserService {

    /**
     * Logger instance for logging events.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    /**
     * Repository for accessing user data.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Utility for encoding and decoding passwords.
     */
    private PasswordEncodingAndDecoding passwordEncodingAndDecoding;

    /**
     * Retrieves a list of all users.
     *
     * @return a list of {@link User} entities.
     */
    public List<User> getAllUserList(){
        LOGGER.info("Retrieving all users");
        List<User> list = userRepository.findAll();
        if (list.isEmpty()) {
            LOGGER.warn("No users found");
        } else {
            LOGGER.info("Found {} users", list.size());
        }
        return list;
    }

    /**
     * Adds a new user based on the provided user request.
     *
     * @param userRequest the {@link UserRequest} containing user details.
     * @return a {@link UserResponse} with the details of the added user.
     * @throws AlreadyExists if a user with the same email already exists.
     */
    public UserResponse addUser(final UserRequest userRequest) {

        LOGGER.info("Adding a new user with email: {}", userRequest.getUserEmail());

        User user = DtoConversion.convertUserRequestToUser(userRequest);
        Optional<User> optionalUser = userRepository.findByUserEmail(userRequest.getUserEmail());
        if (optionalUser.isPresent()){
            LOGGER.error("User with email {} already exists", userRequest.getUserEmail());
            throw new AlreadyExists(ConstantMessage.ALREADY_EXISTS);
        }
        passwordEncodingAndDecoding = new PasswordEncodingAndDecoding();
        user.setUserPassword(passwordEncodingAndDecoding.encodePassword(user.getUserPassword()));
        User savedUser = userRepository.save(user);
        LOGGER.info("Successfully added user with id: {}", savedUser.getUserId());
        UserResponse userResponse = DtoConversion.userToUserResponse(savedUser);
        return userResponse;
    }



    /**
     * Authenticates a user based on the provided login request.
     *
     * @param loginRequest the {@link LoginRequest} containing login details.
     * @return a {@link UserResponse} with the details of the authenticated user.
     * @throws NotFoundException if no user with the given email is found.
     */
    public UserResponse authenticateUser(final LoginRequest loginRequest) {
        LOGGER.info("Authenticating user with email: {}", loginRequest.getUserEmail());

        User user = userRepository.findByUserEmail(loginRequest.getUserEmail())
                .orElseThrow(() -> new NotFoundException(ConstantMessage.NOT_FOUND));
        passwordEncodingAndDecoding = new PasswordEncodingAndDecoding();
        UserResponse userResponse = DtoConversion.userToUserResponse(user);
        LOGGER.info("User with email {} authenticated successfully", loginRequest.getUserEmail());
        return userResponse;
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param userId the ID of the user to retrieve.
     * @return an {@link Optional} containing the {@link User} if found, or empty if not found.
     */
    public Optional<User> getUserById(final Long userId) {
        return userRepository.findById(userId);
    }

}
