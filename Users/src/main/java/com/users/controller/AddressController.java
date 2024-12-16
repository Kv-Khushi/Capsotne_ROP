package com.users.controller;

import com.users.constant.ConstantMessage;
import com.users.dto.AddressResponse;
import com.users.dto.CommonResponse;
import com.users.entities.Address;
import com.users.dto.AddressRequest;
import com.users.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller for handling address-related operations.
 * Provides endpoints for adding, deleting, and retrieving addresses.
 */
@RequestMapping("/address")
@RestController
@Slf4j
public class AddressController {

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
     *  or an {@link HttpStatus#INTERNAL_SERVER_ERROR} status if creation fails
     */
    @PostMapping("/addAddress")
    public ResponseEntity<CommonResponse> addAddress(final @Valid @RequestBody AddressRequest addressRequest) {

        log.info("Received request to add address for userId: {}", addressRequest.getUserId());
        addressService.addAddress(addressRequest);
        CommonResponse response = new CommonResponse(ConstantMessage.ADDRESS_ADD_SUCCESS);
        log.info("Successfully added address for userId: {}", addressRequest.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
        log.info("Received request to retrieve addresses for userId: {}", userId);

        List<Address> addressList = addressService.getAllAddressForUser(userId);
        if (addressList != null && !addressList.isEmpty()) {
            log.info("Successfully retrieved {} addresses for userId: {}", addressList.size(), userId);
            return new ResponseEntity<>(addressList, HttpStatus.OK);
        } else {
            log.error("No addresses found for userId: {}", userId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Updates an existing address.
     *
     * @param addressId the ID of the address to update
     * @param addressRequest the new address data
     * @return the updated address in the response body
     */
    @PutMapping("/{addressId}")
    public ResponseEntity<AddressResponse> updateAddress(
            @PathVariable Long addressId,
            @Valid @RequestBody AddressRequest addressRequest) {

        AddressResponse updatedAddress = addressService.updateAddress(addressId, addressRequest);
        return ResponseEntity.ok(updatedAddress);
    }
}
