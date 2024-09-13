package com.users.controller;

import com.users.entities.Address;
import com.users.dto.AddressRequest;
import com.users.dto.AddressResponse;
import com.users.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller for handling address-related operations.
 * Provides endpoints for adding, deleting, and retrieving addresses.
 */
@RequestMapping("/address")
@RestController
public class AddressController {

    /**
     * Logger instance for logging address-related actions.
     */
    private static final Logger logger = LoggerFactory.getLogger(AddressController.class);

    /**
     * Service layer dependency for address-related operations.
     */
    @Autowired
    private AddressService addressService;

    /**
     * Adds a new address.
     *
     * @param addressRequest the address details to be added
     * @return a {@link ResponseEntity} containing the address response if creation is successful,
     *         or an {@link HttpStatus#INTERNAL_SERVER_ERROR} status if creation fails
     */
    @PostMapping("/addAddress")
    public ResponseEntity<AddressResponse> addAddress(final @Valid @RequestBody AddressRequest addressRequest) {

        logger.info("Received request to add address for userId: {}", addressRequest.getUserId());

        AddressResponse newAddress = addressService.addAddress(addressRequest);
        logger.info("Successfully added address for userId: {}", addressRequest.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(newAddress);
    }

    /**
     * Deletes an address by user ID.
     *
     * @param userId the ID of the user whose address is to be deleted
     * @return a {@link ResponseEntity} with {@link HttpStatus#NO_CONTENT} if deletion is successful,
     *         or {@link HttpStatus#NOT_FOUND} if the address to be deleted is not found
     */
    @DeleteMapping("/deleteAddress/{userId}")
    public ResponseEntity<AddressResponse> deleteAddress(final @PathVariable("userId") Long userId) {
        logger.info("Received request to delete address for userId: {}", userId);
        boolean isDeleted = addressService.deleteAddress(userId);
        if (isDeleted) {
            logger.info("Successfully deleted address for userId: {}", userId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            logger.warn("Address not found for userId: {}", userId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Retrieves all addresses associated with a user.
     *
     * @param userId the ID of the user whose addresses are to be retrieved
     * @return a {@link ResponseEntity} containing a list of addresses if retrieval is successful,
     *         or {@link HttpStatus#NOT_FOUND} if no addresses are found for the given user ID
     */
    @GetMapping("/getAddress/{userId}")
    public ResponseEntity<List<Address>> getAllAddressForUserFind(final @PathVariable Long userId) {
        logger.info("Received request to retrieve addresses for userId: {}", userId);

        List<Address> addressList = addressService.getAllAddressForUser(userId);
        if (addressList != null && !addressList.isEmpty()) {
            logger.info("Successfully retrieved {} addresses for userId: {}", addressList.size(), userId);
            return new ResponseEntity<>(addressList, HttpStatus.OK);
        } else {
            logger.warn("No addresses found for userId: {}", userId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
