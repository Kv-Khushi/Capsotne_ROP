package com.orders.dto;


import lombok.Data;

@Data
public class AddressResponse {
    private Long addressId;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;

}
