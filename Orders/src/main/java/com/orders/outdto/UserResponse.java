package com.orders.outdto;

import com.orders.enums.UserRole;
import lombok.Data;

@Data
public class UserResponse {
    private Long userId;
    private String userName;
    private UserRole userRole;
//    private String userEmail;

}
