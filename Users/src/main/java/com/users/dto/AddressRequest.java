package com.users.dto;

import lombok.Data;

import javax.validation.constraints.*;

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
    @Size(min = 2, message = "Street should have at least 2 characters")
    private String street;

    /**
     * The city part of the address.
     */
    @NotBlank(message = "City is mandatory")
    @Size(min = 2, message = "City should have at least 2 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "City should not contain numbers or special characters")
    private String city;

    /**
     * The state part of the address.
     */
    @NotBlank(message = "State is mandatory")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "State should not contain numbers or special characters")
    @Size(min = 2, message = "State should have at least 2 characters")
    private String state;

    /**
     * The zip code of the address.
     */
    @NotNull(message = "Zip code is mandatory")
    @Min(value = 10000, message = "Zip code must be at least 5 digits")
    @Max(value = 99999, message = "Zip code must be a 5-digit number")
    private Integer zipCode;

    /**
     * The country of the address.
     */
    @NotBlank(message = "Country is mandatory")
    @Size(min = 2, message = "Country should have at least 2 characters")
    private String country;

    /**
     * The ID of the user associated with this address.
     */
    @NotNull(message = "User ID is mandatory")
    private Long userId;

}
