package com.users.indto;

import com.users.entities.UserRole;
import lombok.Data;

@Data
public class UserRequest {

    private Long userId;

    private Long phoneNumber;

    private String userName;

    private String userEmail;

    private String userPassword;

    private UserRole userRole;


}
