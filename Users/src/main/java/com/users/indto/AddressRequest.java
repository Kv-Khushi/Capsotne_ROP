package com.users.indto;


import lombok.Data;

@Data
public class AddressRequest {
    private String street;

    private String city;

    private String state;

    private Integer zipCode;

    private String country;

    private Long userId;

}
