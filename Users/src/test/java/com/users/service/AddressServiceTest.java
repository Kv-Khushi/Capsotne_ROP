package com.users.service;

import com.users.dto.AddressRequest;
import com.users.dto.AddressResponse;
import com.users.dtoconversion.DtoConversion;
import com.users.entities.Address;
import com.users.repository.AddressRepository;
import lombok.var;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.quality.Strictness;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@org.mockito.junit.jupiter.MockitoSettings(strictness = Strictness.LENIENT)
class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressService addressService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllAddressForUser_shouldReturnListOfAddresses() {

        Long userId = 1L;
        Address address1 = new Address();
        Address address2 = new Address();
        List<Address> addresses = Arrays.asList(address1, address2);
        when(addressRepository.findByUserId(userId)).thenReturn(addresses);


        List<Address> result = addressService.getAllAddressForUser(userId);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(addressRepository, times(1)).findByUserId(userId);
    }

    @Test
    void addAddress_shouldReturnAddressResponse() {

        AddressRequest addressRequest = new AddressRequest();
        Address address = new Address();
        Address savedAddress = new Address();
        AddressResponse expectedResponse = new AddressResponse();


        try (var mockedStatic = mockStatic(DtoConversion.class)) {
            mockedStatic.when(() -> DtoConversion.convertAddressRequestToAddress(addressRequest)).thenReturn(address);
            when(addressRepository.save(address)).thenReturn(savedAddress);
            mockedStatic.when(() -> DtoConversion.addressToAddressResponse(savedAddress)).thenReturn(expectedResponse);


            AddressResponse result = addressService.addAddress(addressRequest);


            assertNotNull(result);
            assertEquals(expectedResponse, result);
            mockedStatic.verify(() -> DtoConversion.convertAddressRequestToAddress(addressRequest), times(1));
            verify(addressRepository, times(1)).save(address);
            mockedStatic.verify(() -> DtoConversion.addressToAddressResponse(savedAddress), times(1));
        }
    }

    @Test
    void getAllAddressForUser_shouldReturnEmptyList_whenNoAddressesFound() {

        Long userId = 1L;
        when(addressRepository.findByUserId(userId)).thenReturn(Arrays.asList());


        List<Address> result = addressService.getAllAddressForUser(userId);

        assertNotNull(result);
        assertEquals(0, result.size());  // Asserting that the result is an empty list
        verify(addressRepository, times(1)).findByUserId(userId);
    }

    @Test
    void getAllAddressForUser_shouldThrowException_whenRepositoryFails() {

        Long userId = 1L;
        when(addressRepository.findByUserId(userId)).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> addressService.getAllAddressForUser(userId));
        verify(addressRepository, times(1)).findByUserId(userId);
    }

}
