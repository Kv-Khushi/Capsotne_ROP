package com.users.unit.dto;

import com.users.dto.CommonResponse;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommonResponseTest {

    @Test
    public void testConstructor() {
        String message = "User added successfully.";
        CommonResponse commonResponse = new CommonResponse(message);
        assertEquals(message, commonResponse.getMessage());
    }

    @Test
    public void testGettersAndSetters() {
        String message = "Operation successful.";
        CommonResponse commonResponse = new CommonResponse(message);


        assertEquals(message, commonResponse.getMessage());


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


        assertEquals(response1, response1);
        assertEquals(response1.hashCode(), response1.hashCode());


        assertNotEquals(response1, new Object());


        CommonResponse response3 = new CommonResponse("Different message");
        assertNotEquals(response1, response3);
        assertNotEquals(response1.hashCode(), response3.hashCode());


        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
    }
}
