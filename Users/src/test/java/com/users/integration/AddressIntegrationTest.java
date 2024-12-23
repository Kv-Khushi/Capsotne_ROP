package com.users.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.users.constant.ConstantMessage;
import com.users.dto.AddressRequest;
import com.users.dto.CommonResponse;
import com.users.repository.AddressRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;




import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
@ActiveProfiles("test")
public class AddressIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AddressRepository addressRepository;


    @Test
    @Sql(scripts = "/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD) // Only user creation
    public void testAddAddress_Success() throws Exception {


        addressRepository.deleteAll();
        // Prepare the request for address
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setUserId(1L);  // Use the user that was created in test-data.sql
        addressRequest.setStreet("123 Main St");
        addressRequest.setCity("TestCity");
        addressRequest.setState("IL");
        addressRequest.setZipCode(62704);
        addressRequest.setCountry("ABC");

        // Perform the POST request to add the address
        mockMvc.perform(post("/address/addAddress")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addressRequest)))
                .andExpect(status().isCreated())  // Expecting 201 status
                .andExpect(content().json(objectMapper.writeValueAsString(new CommonResponse(ConstantMessage.ADDRESS_ADD_SUCCESS))));

        // Verify the address was added successfully
        boolean addressExists = addressRepository.existsByUserIdAndStreetAndCityAndStateAndZipCode(
                addressRequest.getUserId(),
                addressRequest.getStreet(),
                addressRequest.getCity(),
                addressRequest.getState(),
                addressRequest.getZipCode()
        );
        assertThat(addressExists).isTrue(); // Ensure the address exists in the database
    }


    @Test
    @Sql(scripts = "/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testAddAddress_AlreadyExists() throws Exception {
        // Prepare the same address request as in the first test case
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setUserId(1L);  // Same user created in the first test
        addressRequest.setStreet("123 Main St");
        addressRequest.setCity("TestCity");
        addressRequest.setState("IL");
        addressRequest.setZipCode(62704);
        addressRequest.setCountry("ABC");

        // Perform the POST request to add the address again, which should cause a conflict
        mockMvc.perform(post("/address/addAddress")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addressRequest)))
                .andExpect(status().isConflict()) // Expecting 409 status code
                .andExpect(jsonPath("$.status").value(409))  // Expecting the status code 409 in the response
                .andExpect(jsonPath("$.message").value("Address already exists")); // Expecting the message field to match the error
    }


    @Test
    public void testAddAddress_InvalidRequest() throws Exception {
        // Prepare an invalid request (missing required fields)
        AddressRequest invalidRequest = new AddressRequest();
        invalidRequest.setUserId(null);
        invalidRequest.setStreet("");
        invalidRequest.setCity("1@#");
        invalidRequest.setState("!");
        invalidRequest.setZipCode(123);
        invalidRequest.setCountry("");

        // Perform the POST request
        mockMvc.perform(post("/address/addAddress")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql(scripts = "/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD) // This will preload user data
    public void testGetAllAddressForUser_Success() throws Exception {
        // Assume the user with userId = 1 has addresses in the test database (from /test-data.sql)

        // Perform the GET request to retrieve all addresses for the user with userId = 1
        mockMvc.perform(get("/address/getAddress/{userId}", 1L)) // Use the correct path variable
                .andExpect(status().isOk()) // Expecting 200 OK status
                .andExpect(jsonPath("$.length()").value(1)) // Expecting 1 address in the response (based on test data)
                .andExpect(jsonPath("$[0].userId").value(1L)) // Verify the userId for the first address
                .andExpect(jsonPath("$[0].street").value("123 Main St")); // Verify the street value (based on test data)
    }


    @Test
    @Sql(scripts = "/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testGetAllAddressForUser_NoAddresses() throws Exception {
        // Test for a user who has no addresses
        // Here, we assume userId = 2 exists in the test database but does not have any associated addresses.

        mockMvc.perform(get("/address/getAddress/{userId}", 2L)) // Use the correct path variable
                .andExpect(status().isNotFound()); // Expecting 404 status since no addresses are found
    }

//    @Test
//    @Sql(scripts = "/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    public void testUpdateAddress_Success() throws Exception {
//
//        assertThat(addressRepository.existsById(1L)).isTrue();
//        AddressRequest addressRequest = new AddressRequest();
//        addressRequest.setUserId(1L);  // Assuming userId is 1
//        addressRequest.setStreet("123 New St");
//        addressRequest.setCity("TestCity");
//        addressRequest.setState("IL");
//        addressRequest.setZipCode(62705);
//        addressRequest.setCountry("USA");
//
//        // Perform PUT request for an address with an existing addressId
//        mockMvc.perform(put("/address/{addressId}", 1L)  // Ensure this addressId exists
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(addressRequest)))
//                .andExpect(status().isOk())  // Expecting 200 OK status
//                .andExpect(jsonPath("$.street").value("123 New St"));
//    }
}
