package com.users.service;

import com.users.dtoconversion.DtoConversion;
import com.users.entities.Address;
import com.users.dto.AddressRequest;
import com.users.dto.AddressResponse;
import com.users.repository.AddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class AddressService {

    /**
     * Logger for logging AddressService operations.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressService.class);

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
        LOGGER.info("Retrieving all addresses for userId: {}", userId);
        List<Address> addresses = addressRepository.findByUserId(userId);
        if (addresses.isEmpty()) {
            LOGGER.warn("No addresses found for userId: {}", userId);
        } else {
            LOGGER.info("Found {} addresses for userId: {}", addresses.size(), userId);
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
        LOGGER.info("Adding a new address for userId: {}", addressRequest.getUserId());

        Address address = DtoConversion.convertAddressRequestToAddress(addressRequest);
        Address savedAddress = addressRepository.save(address);
        AddressResponse addressResponse = DtoConversion.addressToAddressResponse(savedAddress);
        LOGGER.info("Successfully added address with id: {}", savedAddress.getAddressId());
        return addressResponse;
    }

    /**
     * Deletes an address associated with a specific user ID.
     * <p>
     * This method removes the address from the repository using the provided user ID.
     * </p>
     *
     * @param userId the unique identifier of the user whose address is to be deleted
     * @return {@code true} if the address was successfully deleted, {@code false} otherwise
     */
    public boolean deleteAddress(final Long userId){
        LOGGER.info("Attempting to delete address for userId: {}", userId);
        addressRepository.deleteById(userId);
        LOGGER.info("Successfully deleted address for userId: {}", userId);
        return true;
    }
}
