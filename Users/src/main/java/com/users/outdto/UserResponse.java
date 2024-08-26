package com.users.outdto;

import lombok.Data;

@Data
public class UserResponse {

    private Long userId;

    private Long phoneNumber;

    private String userName;

    private String userEmail;

    private String userPassword;

    private Long wallet;
}
