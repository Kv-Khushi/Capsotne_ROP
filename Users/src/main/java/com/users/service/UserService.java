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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for handling user-related operations.
 */
@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    private PasswordEncodingAndDecoding passwordEncodingAndDecoding;

    /**
     * Retrieves a list of all users.
     *
     * @return a list of {@link User} entities.
     */
    public List<User> getAllUserList(){
        logger.info("Retrieving all users");
        List<User> list = userRepository.findAll();
        if (list.isEmpty()) {
            logger.warn("No users found");
        } else {
            logger.info("Found {} users", list.size());
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
    public UserResponse addUser(UserRequest userRequest) {

        logger.info("Adding a new user with email: {}", userRequest.getUserEmail());

        User user = DtoConversion.convertUserRequestToUser(userRequest);
        Optional<User> optionalUser = userRepository.findByUserEmail(userRequest.getUserEmail());
        if (optionalUser.isPresent()){
            logger.error("User with email {} already exists", userRequest.getUserEmail());
            throw new AlreadyExists(ConstantMessage.ALREADY_EXISTS);
        }
        passwordEncodingAndDecoding = new PasswordEncodingAndDecoding();
        user.setUserPassword(passwordEncodingAndDecoding.encodePassword(user.getUserPassword()));
        User savedUser = userRepository.save(user);
        logger.info("Successfully added user with id: {}", savedUser.getUserId());
        UserResponse userResponse = DtoConversion.userToUserResponse(savedUser);
        return userResponse;
    }

    /**
     * Deletes a user by their ID.
     *
     * @param userId the ID of the user to be deleted.
     * @return {@code true} if the user was successfully deleted.
     * @throws NotFoundException if no user with the given ID is found.
     */
    public boolean deleteUser(Long userId){
        logger.info("Deleting user with id: {}", userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ConstantMessage.NOT_FOUND));
        userRepository.deleteById(userId);
        return true;
    }

    /**
     * Authenticates a user based on the provided login request.
     *
     * @param loginRequest the {@link LoginRequest} containing login details.
     * @return a {@link UserResponse} with the details of the authenticated user.
     * @throws NotFoundException if no user with the given email is found.
     */
    public UserResponse authenticateUser(LoginRequest loginRequest) {
        logger.info("Authenticating user with email: {}", loginRequest.getUserEmail());

        User user = userRepository.findByUserEmail(loginRequest.getUserEmail())
                .orElseThrow(() -> new NotFoundException(ConstantMessage.NOT_FOUND));
        passwordEncodingAndDecoding = new PasswordEncodingAndDecoding();
        UserResponse userResponse = DtoConversion.userToUserResponse(user);
        logger.info("User with email {} authenticated successfully", loginRequest.getUserEmail());
        return userResponse;
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

}
