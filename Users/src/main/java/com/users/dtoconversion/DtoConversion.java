package com.users.dtoconversion;

import com.users.entities.Address;
import com.users.entities.User;
import com.users.dto.AddressRequest;
import com.users.dto.UserRequest;
import com.users.dto.AddressResponse;
import com.users.dto.UserResponse;

/**
 * Utility class for converting between DTOs (Data Transfer Objects) and entities.
 */
public class DtoConversion {

    /**
     * Converts a {@link UserRequest} DTO to a {@link User} entity.
     *
     * @param userRequest the {@link UserRequest} DTO containing user details
     * @return a {@link User} entity populated with data from the provided DTO
     */
    public static User convertUserRequestToUser(final UserRequest userRequest) {
        User user = new User();
        user.setUserName(userRequest.getUserName());
        user.setUserPassword(userRequest.getUserPassword());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setUserEmail(userRequest.getUserEmail().toLowerCase());
        user.setUserRole(userRequest.getUserRole());
        return user;
    }

    /**
     * Converts a {@link User} entity to a {@link UserResponse} DTO.
     *
     * @param user the {@link User} entity to be converted
     * @return a {@link UserResponse} DTO populated with data from the provided entity
     */
    public static UserResponse userToUserResponse(final User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(user.getUserId());
        userResponse.setUserEmail(user.getUserEmail());
        userResponse.setUserName(user.getUserName());
        userResponse.setPhoneNumber(user.getPhoneNumber());
        userResponse.setUserPassword(user.getUserPassword());
        userResponse.setWallet(user.getWallet());
        userResponse.setUserRole(user.getUserRole());
        return userResponse;
    }

    /**
     * Converts an {@link AddressRequest} DTO to an {@link Address} entity.
     *
     * @param addressRequest the {@link AddressRequest} DTO containing address details
     * @return an {@link Address} entity populated with data from the provided DTO
     */
    public static Address convertAddressRequestToAddress(final AddressRequest addressRequest) {
        Address address = new Address();
        address.setStreet(addressRequest.getStreet());
        address.setCity(addressRequest.getCity());
        address.setState(addressRequest.getState());
        address.setZipCode(addressRequest.getZipCode());
        address.setCountry(addressRequest.getCountry());
        address.setUserId(addressRequest.getUserId());
        return address;
    }

    /**
     * Converts an {@link Address} entity to an {@link AddressResponse} DTO.
     *
     * @param address the {@link Address} entity to be converted
     * @return an {@link AddressResponse} DTO populated with data from the provided entity
     */
    public static AddressResponse addressToAddressResponse(final Address address) {
        AddressResponse addressResponse = new AddressResponse();
        addressResponse.setStreet(address.getStreet());
        addressResponse.setCity(address.getCity());
        addressResponse.setState(address.getState());
        addressResponse.setZipCode(address.getZipCode());
        addressResponse.setCountry(address.getCountry());
        addressResponse.setUserId(address.getUserId());
        return addressResponse;
    }
}
