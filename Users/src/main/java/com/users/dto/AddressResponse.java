package com.users.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for address responses.
 * <p>
 * This class is used to encapsulate the data returned in response to address-related requests.
 * It includes information such as the street, city, state, zip code, country, and associated user ID.
 * </p>
 */
@Data
public class AddressResponse {

    /**
     * The street part of the address.
     */
    private String street;

    /**
     * The city part of the address.
     */
    private String city;

    /**
     * The state part of the address.
     */
    private String state;

    /**
     * The zip code of the address.
     */
    private Integer zipCode;

    /**
     * The country of the address.
     */
    private String country;

    /**
     * The ID of the user associated with this address.
     */
    private Long userId;
}

