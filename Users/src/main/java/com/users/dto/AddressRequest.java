package com.users.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Data Transfer Object (DTO) for addressing requests.
 * <p>
 * This class is used to encapsulate the data needed to create or update an address
 * when interacting with the system. It includes information such as the street, city,
 * state, zip code, country, and associated user ID.
 * </p>
 */
@Data
public class AddressRequest {

    /**
     * The street part of the address.
     */
    @NotBlank(message = "Street is mandatory")

    private String street;

    /**
     * The city part of the address.
     */
    @NotBlank(message = "City is mandatory")
    private String city;

    /**
     * The state part of the address.
     */
    @NotBlank(message = "State is mandatory")
    private String state;

    /**
     * The zip code of the address.
     */
    @NotNull(message = "Zip code is mandatory")
    private Integer zipCode;

    /**
     * The country of the address.
     */
    @NotBlank(message = "Country is mandatory")
    private String country;

    /**
     * The ID of the user associated with this address.
     */
    @NotNull(message = "User ID is mandatory")
    private Long userId;

}
