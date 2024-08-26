package com.users.service;

import com.users.dtoconversion.DtoConversion;
import com.users.entities.Address;
import com.users.indto.AddressRequest;
import com.users.outdto.AddressResponse;
import com.users.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public List<Address> getAllAddressForUser(Long userId){
        return addressRepository.findByUserId(userId);
    }

     public AddressResponse addAddress(AddressRequest addressRequest){
         Address address = DtoConversion.convertAddressRequestToAddress(addressRequest);
         Address savedAddress =addressRepository.save(address);
         AddressResponse addressResponse = DtoConversion.addressToAddressResponse(savedAddress);
         return addressResponse;
    }

    public boolean deleteAddress(Long userId){
         addressRepository.deleteById(userId);
         return true;
    }
}
