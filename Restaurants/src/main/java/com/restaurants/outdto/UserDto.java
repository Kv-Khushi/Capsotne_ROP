package com.restaurants.outdto;

import lombok.Data;

@Data
public class UserDto {

    private Long userId;

    private Long phoneNumber;

    private String userName;

    private String userEmail;

    private String userPassword;

    private Long wallet;
}
