package com.users.service;

import com.users.constant.ConstantMessage;
import com.users.dtoconversion.DtoConversion;
import com.users.entities.Address;
import com.users.dto.AddressRequest;
import com.users.dto.AddressResponse;
import com.users.exception.ResourceAlreadyExists;
import com.users.exception.ResourceNotFoundException;
import com.users.repository.AddressRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing {@link Address} entities.
 * <p>
 * This class provides business logic for operations related to addresses, including retrieving, adding, and deleting addresses.
 * It interacts with the {@link AddressRepository} to perform these operations and uses DTO conversion methods to transform data.
 * </p>
 */
@Service
@Slf4j
public class AddressService {
    /**
     * Repository for performing CRUD operations on Address entities.
     */
    @Autowired
    private AddressRepository addressRepository;

    /**
     * Retrieves a list of all addresses associated with a specific user ID.
     * <p>
     * This method queries the repository for addresses linked to the given user ID.
     * </p>
     *
     * @param userId the unique identifier of the user whose addresses are to be retrieved
     * @return a list of {@link Address} entities associated with the specified user ID
     */
    public List<Address> getAllAddressForUser(final Long userId){
        log.info("Retrieving all addresses for userId: {}", userId);
        List<Address> addresses = addressRepository.findByUserId(userId);
        if (addresses.isEmpty()) {
            log.error("No addresses found for userId: {}", userId);
        } else {
            log.info("Found {} addresses for userId: {}", addresses.size(), userId);
        }
        return addresses;
    }

    /**
     * Adds a new address based on the provided request data.
     * <p>
     * This method converts the {@link AddressRequest} to an {@link Address} entity, saves it to the repository,
     * and then converts the saved address to an {@link AddressResponse} for return.
     * </p>
     *
     * @param addressRequest the data transfer object containing address details to be added
     * @return the {@link AddressResponse} containing details of the newly added address
     */
    public AddressResponse addAddress(final AddressRequest addressRequest){
        log.info("Adding a new address for userId: {}", addressRequest.getUserId());

        boolean addressExists = addressRepository.existsByUserIdAndStreetAndCityAndStateAndZipCode(
                addressRequest.getUserId(),
                addressRequest.getStreet(),
                addressRequest.getCity(),
                addressRequest.getState(),
                addressRequest.getZipCode()
        );

        if (addressExists) {
            log.error("Address already exists for userId: {}", addressRequest.getUserId());
            throw new ResourceAlreadyExists(ConstantMessage.ADDRESS_ALREADY_EXISTS);
        }
        Address address = DtoConversion.convertAddressRequestToAddress(addressRequest);
        Address savedAddress = addressRepository.save(address);
        AddressResponse addressResponse = DtoConversion.addressToAddressResponse(savedAddress);
        log.info("Successfully added address with id: {}", savedAddress.getAddressId());
        return addressResponse;
    }

    /**
     * Updates an existing address.
     *
     * @param addressId the ID of the address to update
     * @param addressRequest the new data to update the address with
     * @return the updated {@link AddressResponse}
     * @throws ResourceNotFoundException if the address with the given ID is not found
     */
    public AddressResponse updateAddress(Long addressId, AddressRequest addressRequest) {
        log.info("Updating address with id: {}", addressId);

        Address existingAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + addressId));

        existingAddress.setStreet(addressRequest.getStreet());
        existingAddress.setCity(addressRequest.getCity());
        existingAddress.setState(addressRequest.getState());
        existingAddress.setZipCode(addressRequest.getZipCode());
        existingAddress.setCountry(addressRequest.getCountry());

        Address updatedAddress = addressRepository.save(existingAddress);
        log.info("Successfully updated address with id: {}", updatedAddress.getAddressId());
        return DtoConversion.addressToAddressResponse(updatedAddress);
    }
}
