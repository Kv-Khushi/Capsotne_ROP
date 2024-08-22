package com.users.indto;

import lombok.Data;

@Data

public class LoginRequest {
    private String userEmail;
    private String userPassword;

    @Override
    public String toString() {
        return "LoginRequest{" +
                "userEmail='" + userEmail + '\'' +
                ", userPassword='" + userPassword + '\'' +
                '}';
    }
}
