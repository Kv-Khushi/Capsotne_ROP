package com.orders.dto;


import lombok.Data;


/**
 * Data Transfer Object (DTO) for sending address details as a response.
 * This class contains all the relevant address information such as street, city,
 * state, postal code, and country.
 * <p>
 * Lombok's {@code @Data} annotation is used to automatically generate getters,
 * setters, equals, hashCode, and toString methods.
 * </p>
 */
@Data
public class AddressResponse {

    /**
     * Unique identifier for the address.
     */
    private Long addressId;

    /**
     * Street address of the user.
     */
    private String street;

    /**
     * City of the user's address.
     */
    private String city;

    /**
     * State or region of the user's address.
     */
    private String state;


    /**
     * Postal or ZIP code of the user's address.
     */
    private String postalCode;

    /**
     * Country of the user's address.
     */
    private String country;

}
