package com.users.outdto;

import lombok.Data;

@Data
public class AddressResponse {
    private String street;

    private String city;

    private String state;

    private Integer zipCode;

    private String country;

    private Long userId;
}
