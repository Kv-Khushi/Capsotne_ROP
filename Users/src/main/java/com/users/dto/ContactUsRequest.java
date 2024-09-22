package com.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object (DTO) for the "Contact Us" form submission.
 * <p>
 * Represents the information submitted by a user when they want to contact support or the admin team.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactUsRequest {

    /**
     * The name of the user submitting the contact form.
     */
    private String name;

    /**
     * The email addresses of the recipients.
     */
    private List<String> emails;

    /**
     * The subject of the contact message.
     */
    private String subject;

    /**
     * The detailed message from the user.
     */
    private String message;
}
