package com.users.outdto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * A response object that holds the message after a user is added.
 */
@Data

@AllArgsConstructor
public class UserAddResponse {
    /**
     * A message indicating the result of the user addition.
     */
    private String message;
}
