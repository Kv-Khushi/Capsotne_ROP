package com.users.controller;
import com.users.entities.Address;
import com.users.indto.AddressRequest;
import com.users.outdto.AddressResponse;
import com.users.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@RequestMapping("/address")
@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/addAddress")
    public ResponseEntity<AddressResponse> addAddress(@RequestBody AddressRequest addressRequest){
        AddressResponse newAddress= addressService.addAddress(addressRequest);
        if(newAddress != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(newAddress);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @DeleteMapping("/deleteAddress/{userId}")
    public ResponseEntity<AddressResponse> deleteAddress(final @PathVariable("userId")Long userId){
        boolean isDeleted=addressService.deleteAddress(userId);
        if(isDeleted){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/getAddress/{userId}")
    public ResponseEntity<List<Address>> getAllAddressForUserFind(@PathVariable Long userId){
        List<Address> addressList=addressService.getAllAddressForUser(userId);
        return new ResponseEntity<>(addressList,HttpStatus.OK);
    }

}
