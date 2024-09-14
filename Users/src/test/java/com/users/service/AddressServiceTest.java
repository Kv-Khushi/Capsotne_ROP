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
        // Given
        Long userId = 1L;
        Address address1 = new Address();
        Address address2 = new Address();
        List<Address> addresses = Arrays.asList(address1, address2);
        when(addressRepository.findByUserId(userId)).thenReturn(addresses);

        // When
        List<Address> result = addressService.getAllAddressForUser(userId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(addressRepository, times(1)).findByUserId(userId);
    }

    @Test
    void addAddress_shouldReturnAddressResponse() {
        // Given
        AddressRequest addressRequest = new AddressRequest();
        Address address = new Address();
        Address savedAddress = new Address();
        AddressResponse expectedResponse = new AddressResponse();

        // Mock static methods using Mockito
        try (var mockedStatic = mockStatic(DtoConversion.class)) {
            mockedStatic.when(() -> DtoConversion.convertAddressRequestToAddress(addressRequest)).thenReturn(address);
            when(addressRepository.save(address)).thenReturn(savedAddress);
            mockedStatic.when(() -> DtoConversion.addressToAddressResponse(savedAddress)).thenReturn(expectedResponse);

            // When
            AddressResponse result = addressService.addAddress(addressRequest);

            // Then
            assertNotNull(result);
            assertEquals(expectedResponse, result);
            mockedStatic.verify(() -> DtoConversion.convertAddressRequestToAddress(addressRequest), times(1));
            verify(addressRepository, times(1)).save(address);
            mockedStatic.verify(() -> DtoConversion.addressToAddressResponse(savedAddress), times(1));
        }
    }

    @Test
    void deleteAddress_shouldReturnTrue() {
        // Given
        Long addressId = 1L;

        // When
        boolean result = addressService.deleteAddress(addressId);

        // Then
        assertTrue(result);
        verify(addressRepository, times(1)).deleteById(addressId);
    }

    @Test
    void getAllAddressForUser_shouldReturnEmptyList_whenNoAddressesFound() {
        // Given
        Long userId = 1L;
        when(addressRepository.findByUserId(userId)).thenReturn(Arrays.asList());

        // When
        List<Address> result = addressService.getAllAddressForUser(userId);

        // Then
        assertNotNull(result);
        assertEquals(0, result.size());  // Asserting that the result is an empty list
        verify(addressRepository, times(1)).findByUserId(userId);
    }

    @Test
    void deleteAddress_shouldNotThrowError_whenAddressDoesNotExist() {
        // Given
        Long addressId = 100L; // Non-existent address ID
        doNothing().when(addressRepository).deleteById(addressId);  // Simulate deletion of a non-existent address

        // When
        boolean result = addressService.deleteAddress(addressId);

        // Then
        assertTrue(result);  // Even though the address doesn't exist, it should return true
        verify(addressRepository, times(1)).deleteById(addressId);  // Ensure deleteById was called
    }

    @Test
    void getAllAddressForUser_shouldThrowException_whenRepositoryFails() {
        // Given
        Long userId = 1L;
        when(addressRepository.findByUserId(userId)).thenThrow(new RuntimeException("Database error"));

        // When & Then
        assertThrows(RuntimeException.class, () -> addressService.getAllAddressForUser(userId));
        verify(addressRepository, times(1)).findByUserId(userId);
    }

    @Test
    void deleteAddress_shouldThrowException_whenRepositoryFails() {
        // Given
        Long addressId = 1L;
        doThrow(new RuntimeException("Database error")).when(addressRepository).deleteById(addressId);

        // When & Then
        assertThrows(RuntimeException.class, () -> addressService.deleteAddress(addressId));
        verify(addressRepository, times(1)).deleteById(addressId);
    }
}
