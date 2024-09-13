package com.users.dto;


import lombok.Data;

@Data
public class ContactUsResponse {

    private Long userId;
    private String subject;
    private String message;

}