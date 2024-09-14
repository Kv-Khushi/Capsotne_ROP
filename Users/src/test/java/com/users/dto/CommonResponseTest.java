package com.users.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommonResponseTest {

    @Test
    public void testConstructor() {
        String message = "User added successfully.";
        CommonResponse commonResponse = new CommonResponse(message);

        // Verify that the constructor sets the message correctly
        assertEquals(message, commonResponse.getMessage());
    }

    @Test
    public void testGettersAndSetters() {
        String message = "Operation successful.";
        CommonResponse commonResponse = new CommonResponse(message);

        // Test the message field
        assertEquals(message, commonResponse.getMessage());

        // Update the message and verify
        String newMessage = "Updated message.";
        commonResponse.setMessage(newMessage);
        assertEquals(newMessage, commonResponse.getMessage());
    }

    @Test
    public void testToString() {
        String message = "User added successfully.";
        CommonResponse commonResponse = new CommonResponse(message);

        String expectedString = "CommonResponse(message=" + message + ")";
        assertEquals(expectedString, commonResponse.toString());
    }

    @Test
    public void testEqualsAndHashCode() {
        String message = "User added successfully.";

        CommonResponse response1 = new CommonResponse(message);
        CommonResponse response2 = new CommonResponse(message);

        // Test equals and hashCode with the same object
        assertEquals(response1, response1);
        assertEquals(response1.hashCode(), response1.hashCode());

        // Test equals and hashCode with different object types
        assertNotEquals(response1, new Object());

        // Test equals and hashCode with different values
        CommonResponse response3 = new CommonResponse("Different message");
        assertNotEquals(response1, response3);
        assertNotEquals(response1.hashCode(), response3.hashCode());

        // Test equals and hashCode with the same values
        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
    }
}
