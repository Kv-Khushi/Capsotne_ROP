package com.users.service;

import com.users.constant.ConstantMessage;
import com.users.dto.*;
import com.users.dtoconversion.DtoConversion;
import com.users.entities.Address;
import com.users.entities.User;
import com.users.enums.UserRole;
import com.users.exception.InvalidRequestException;
import com.users.exception.ResourceAlreadyExists;
import com.users.exception.ResourceNotFoundException;
import com.users.exception.UnauthorizedAccessException;
import com.users.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.users.passwordencryption.PasswordEncodingAndDecoding;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Service class for handling user-related operations.
 */
@Service
@Slf4j
public class UserService {

    /**
     * Repository for accessing user data.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Service for sending emails.
     */
    @Autowired
    private EmailService emailService;

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
        log.info("Retrieving all users");
        List<User> list = userRepository.findAll();
        if (list.isEmpty()) {
            log.error("No users found");
        } else {
            log.info("Found {} users", list.size());
        }
        return list;
    }

    /**
     * Adds a new user based on the provided user request.
     *
     * @param userRequest the {@link UserRequest} containing user details.
     * @return a {@link UserResponse} with the details of the added user.
     * @throws ResourceAlreadyExists if a user with the same email already exists.
     */
    public UserResponse addUser(final UserRequest userRequest) {

        log.info("Adding a new user with email: {}", userRequest.getUserEmail());

        User user = DtoConversion.convertUserRequestToUser(userRequest);

        Optional<User> optionalUser = userRepository.findByUserEmail(userRequest.getUserEmail().toLowerCase());
        if (optionalUser.isPresent()){
            log.error("User with email {} already exists", userRequest.getUserEmail());
            throw new ResourceAlreadyExists(ConstantMessage.ALREADY_EXISTS);
        }

        if (userRequest.getUserRole() == UserRole.RESTAURANT_OWNER) {
            user.setWallet(null);
        } else {
            user.setWallet(ConstantMessage.WALLET_AMOUNT); // default wallet balance
        }

     passwordEncodingAndDecoding = new PasswordEncodingAndDecoding();

        user.setUserPassword(passwordEncodingAndDecoding.encodePassword(user.getUserPassword()));
        User savedUser = userRepository.save(user);
        log.info("Successfully added user with id: {}", savedUser.getUserId());
        UserResponse userResponse = DtoConversion.userToUserResponse(savedUser);
        return userResponse;
    }

    /**
     * Authenticates a user based on the provided login request.
     *
     * @param loginRequest the {@link LoginRequest} containing login details.
     * @return a {@link UserResponse} with the details of the authenticated user.
     * @throws ResourceNotFoundException if no user with the given email is found.
     */
    public UserResponse authenticateUser(final LoginRequest loginRequest) {
        log.info("Authenticating user with email: {}", loginRequest.getUserEmail());

        User user = userRepository.findByUserEmail(loginRequest.getUserEmail())
                .orElseThrow(() -> new ResourceNotFoundException(ConstantMessage.NOT_FOUND));
        passwordEncodingAndDecoding = new PasswordEncodingAndDecoding();
        if (!passwordEncodingAndDecoding.decodePassword(user.getUserPassword()).equals(loginRequest.getUserPassword())){
            throw new UnauthorizedAccessException(ConstantMessage.INVALID_CREDENTIALS);
        }

        UserResponse userResponse = DtoConversion.userToUserResponse(user);

        log.info("User with email {} authenticated successfully", loginRequest.getUserEmail());
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


    /**
     * Updates the wallet balance for the specified user.
     *
     * @param userId the ID of the user whose wallet balance is to be updated
     * @param newBalance the new balance to set in the user's wallet
     */
    public void updateWalletBalance(final Long userId, final Double newBalance) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(ConstantMessage.NOT_FOUND));
        if(user.getUserRole().equals(UserRole.RESTAURANT_OWNER)){
            throw new InvalidRequestException(ConstantMessage.OWNER_CAN_N0T_UPDATE_WALLET);
        }
        user.setWallet(newBalance);
        userRepository.save(user);
    }

    /**
     * Sends an email in response to a "Contact Us" form submission.
     * <p>
     * This method sends an email to the support team using the details provided
     * in the {@code contactUsRequest}. It sends the email to a list of predefined support
     * email addresses.
     * </p>
     *
     * @param contactUsRequest the request containing the customer's name, subject, and message
     * @return a {@link CommonResponse} indicating whether the email was sent successfully
     */
    public CommonResponse sendContactUsEmail(final ContactUsRequest contactUsRequest) {
        List<String> supportEmails = Arrays.asList("khushi.vyas@nucleusteq.com",
                 "vyaskhushi2407@gmail.com","purviv939@gmail.com");

        String subject = contactUsRequest.getSubject();
        String customerName = contactUsRequest.getName();
        String customMessage = contactUsRequest.getMessage();

        emailService.sendContactUsEmail(supportEmails, subject, customerName, customMessage);
        return new CommonResponse(ConstantMessage.MAIL_SENT_SUCCESSFULLY);
    }
}

