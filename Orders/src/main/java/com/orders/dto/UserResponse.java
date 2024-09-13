package com.orders.dto;

import com.orders.enums.UserRole;
import lombok.Data;

@Data
public class UserResponse {
    private Long userId;
    private String userName;
    private UserRole userRole;
    private Double wallet;
//    private String userEmail;

}
