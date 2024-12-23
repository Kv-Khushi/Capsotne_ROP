package com.users.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.users.constant.ConstantMessage;
import com.users.dto.AddressRequest;
import com.users.dto.CommonResponse;
import com.users.entities.Address;
import com.users.repository.AddressRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    public void testAddAddress_Success() throws Exception {
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setUserId(1L);
        addressRequest.setStreet("123 Main St");
        addressRequest.setCity("Springfield");
        addressRequest.setState("IL");
        addressRequest.setZipCode(62704);

        System.out.println("Sending POST request to /addresses/addAddress");

        mockMvc.perform(post("/addresses/addAddress")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addressRequest)))
                .andDo(print()) // This will print the request and response details
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(new CommonResponse(ConstantMessage.ADDRESS_ADD_SUCCESS))));
    }
    

    @Test
    public void testAddAddress_AlreadyExists() throws Exception {
        // Pre-existing address in the repository
        Address existingAddress = new Address();
        existingAddress.setUserId(1L);
        existingAddress.setStreet("123 Main St");
        existingAddress.setCity("Springfield");
        existingAddress.setState("IL");
        existingAddress.setZipCode(62704);

        addressRepository.save(existingAddress);

        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setUserId(1L);
        addressRequest.setStreet("123 Main St");
        addressRequest.setCity("Springfield");
        addressRequest.setState("IL");
        addressRequest.setZipCode(62704);

        mockMvc.perform(post("/addresses/addAddress")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addressRequest)))
                .andExpect(status().isConflict())
                .andExpect(content().string(ConstantMessage.ADDRESS_ALREADY_EXISTS));
    }

    @Test
    public void testAddAddress_InvalidInput() throws Exception {
        AddressRequest invalidRequest = new AddressRequest();
        invalidRequest.setUserId(null); // Missing required userId
        invalidRequest.setStreet("");   // Invalid empty street
        invalidRequest.setCity("");
        invalidRequest.setState("");
        invalidRequest.setZipCode( 7);

        mockMvc.perform(post("/addresses/addAddress")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }
}
